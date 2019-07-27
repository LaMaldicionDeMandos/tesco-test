package coop.tecso.examen.controller;

import com.google.common.collect.Lists;
import coop.tecso.examen.controller.entitybuilder.AccountBuilder;
import coop.tecso.examen.controller.entitybuilder.OrderBuilder;
import coop.tecso.examen.dto.AccountDto;
import coop.tecso.examen.dto.OrderDto;
import coop.tecso.examen.exception.AccountNotFoundException;
import coop.tecso.examen.exception.InvalidOperationException;
import coop.tecso.examen.model.Order;
import coop.tecso.examen.service.AccountService;
import coop.tecso.examen.model.Account;
import org.apache.commons.lang3.RandomUtils;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Date;

import static coop.tecso.examen.model.Currency.PESO;
import static coop.tecso.examen.model.OrderType.CREDIT;
import static coop.tecso.examen.model.OrderType.DEBIT;
import static java.math.BigDecimal.TEN;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class )
public class AccountControllerTest {

	private final static AccountBuilder accountBuilder = new AccountBuilder();
	private final static OrderBuilder orderBuilder = new OrderBuilder();
	public static final long ACCOUNT_NUMBER = 1l;
	public static final long ACCOUNT_ID = 1l;
	public static final String CURRENCY_PESO = "PESO";
	public static final int ZERO_BALANCE = 0;
	private static final OrderDto CREDIT_ORDER = new OrderDto(1l, new Date(), CREDIT, "saraza", TEN);
	private static final OrderDto DEBIT_ORDER = new OrderDto(1l, new Date(), DEBIT, "saraza", BigDecimal.valueOf(10000));

	@MockBean
	private AccountService accountService;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private AccountController controller;

	private AccountDto accountDto;
	private ResultActions response;
	private boolean DELETED = true;
	private boolean NOT_DELETED = false;
	private String root;

	@Before
	public void before() {
		root = controller.getClass().getAnnotation(RequestMapping.class).value()[0];
	}

	@Test
	public void newAccount() throws Exception {
		givenAnAccountDto();
		givenAnAccountServiceWithNewAccount();

		whenPostNewAccount(accountDto);

		thenResponseShouldReturnAnNewAccount();
	}

	@Test
	public void newAccountWithoutBody() throws Exception {
		givenAnAccountDto();

		whenPostNewAccountWithoutBody();

		thenResponseShouldReturnBadRequest();
	}

	@Test
	public void newAccountWithBadBody() throws Exception {
		givenABadAccountDto();
		givenAnAccountServiceWithNewAccount();

		whenPostNewAccount(accountDto);

		thenResponseShouldReturnBadRequest();
	}

	@Test
	public void newAccountWithBadCurrency() throws Exception {
		givenAnAccountServiceWithNewAccount();

		whenPostNewAccountWithBadCurrency();

		thenResponseShouldReturnBadRequest();
	}

	@Test
	public void deleteAccount() throws Exception {
		givenAnAccountServiceWithAnAccountToDelete();

		whenDeleteAccount();

		thenResponseShouldDeleteAccount();
	}

	@Test
	public void deleteAccountWithOrders() throws Exception {
		givenAnAccountServiceWithAnAccountToDeleteUnallowed();

		whenDeleteAccount();

		thenResponseShouldNotDeleteAccount();
	}

	@Test
	public void deleteAccountThatNotExists() throws Exception {
		givenAnAccountServiceWithoutAccount();

		whenDeleteAccount();

		thenResponseShouldNotFoundAccount();
	}

	@Test
	public void listAccounts() throws Exception {
		givenAnAccountServiceWithAccounts();

		whenListAccounts();

		thenResponseShouldReturnAccounts();
	}

	@Test
	public void addOrder() throws Exception {
		givenAnAccountServiceThatAcceptOrder(CREDIT_ORDER);

		whenAddOrder(CREDIT_ORDER);

		thenResponseShouldReturnNewAccountWithOrder();
	}

	@Test
	public void addInvalidOrder() throws Exception {
		givenAnAccountServiceThatNotAcceptOrder();

		whenAddOrder(DEBIT_ORDER);

		thenResponseShouldReturnBadRequest();
	}

	@Test
	public void listOrders() throws Exception {
		givenAnAccountServiceWithOrdersToAccount(ACCOUNT_ID);

		whenListOrders(ACCOUNT_ID);

		thenResponseShouldReturnOrders();
	}

	@Test
	public void listOrdersOfNonExistentAccount() throws Exception {
		givenAnAccountServiceWithoutAccount();

		whenListOrders(ACCOUNT_ID);

		thenResponseShouldNotFoundAccount();
	}

	private void givenAnAccountServiceWithOrdersToAccount(long accountId) {
		when(accountService.listOrders(accountId)).thenReturn(Lists.newArrayList(
				createRandomOrder(1l),
				createRandomOrder(2l),
				createRandomOrder(3l)
		));
	}

	private void givenAnAccountServiceThatNotAcceptOrder() {
		when(accountService.addOrder(eq(ACCOUNT_ID), any())).thenThrow(InvalidOperationException.class);
	}

	private void givenAnAccountServiceThatAcceptOrder(OrderDto orderDto) {
		Order order = new Order(new Date(), orderDto.getType(), orderDto.getDescription(), orderDto.getAmount());
		order.setId(1l);
		Account account = new Account(ACCOUNT_NUMBER, PESO);
		account.setId(ACCOUNT_ID);
		account.addOrder(order);
		when(accountService.addOrder(eq(ACCOUNT_ID), any())).thenReturn(account);
	}

	private void whenListAccounts() throws Exception {
		response = mvc.perform(get(root).contentType(APPLICATION_JSON));
	}

	private void givenAnAccountServiceWithAccounts() {
		when(accountService.getAccounts()).thenReturn(Lists.newArrayList(
				createAccount(1l, 1001l),
				createAccount(2l, 1002l),
				createAccount(3l, 1003l)
		));
	}

	private void givenAnAccountServiceWithoutAccount() {
		when(accountService.deleteAccount(ACCOUNT_ID)).thenThrow(AccountNotFoundException.class);
		when(accountService.listOrders(ACCOUNT_ID)).thenThrow(AccountNotFoundException.class);
	}

	private void givenAnAccountServiceWithAnAccountToDeleteUnallowed() {
		when(accountService.deleteAccount(ACCOUNT_ID)).thenReturn(NOT_DELETED);
	}

	private void givenAnAccountServiceWithAnAccountToDelete() {
		when(accountService.deleteAccount(ACCOUNT_ID)).thenReturn(DELETED);
	}

	private void givenABadAccountDto() {
		accountDto = accountBuilder.withAccountNumber(1l).build();
	}

	private void givenAnAccountServiceWithNewAccount() {
		Account account = new Account(ACCOUNT_NUMBER, PESO);
		account.setId(ACCOUNT_ID);
		when(accountService.newAccount(ACCOUNT_NUMBER, PESO)).thenReturn(account);
	}

	private void givenAnAccountDto() {
		accountDto = accountBuilder.buildValid();
	}

	private void whenPostNewAccountWithBadCurrency() throws Exception {
		response = mvc.perform(post(root).content("{\"accountNumber\":1,\"currency\":\"REAL\"}").contentType(APPLICATION_JSON));
	}

	private void whenPostNewAccount(AccountDto accountDto) throws Exception {
		response = mvc.perform(post(root).content(accountBuilder.buildAsString(accountDto)).contentType(APPLICATION_JSON));
	}

	private void whenAddOrder(OrderDto orderDto) throws Exception {
		response = mvc.perform(post(root + "/1/order")
				.content(orderBuilder.buildAsString(orderDto)).contentType(APPLICATION_JSON));
	}

	private void whenDeleteAccount() throws Exception {
		response = mvc.perform(delete(root + "/" + ACCOUNT_ID).contentType(APPLICATION_JSON));
	}

	private void whenPostNewAccountWithoutBody() throws Exception {
		response = mvc.perform(post(root).contentType(APPLICATION_JSON));
	}


	private void whenListOrders(long accountId) throws Exception {
		response = mvc.perform(get(root + "/" + accountId + "/order").contentType(APPLICATION_JSON));
	}

	private void thenResponseShouldReturnAnNewAccount() throws Exception {
		response.andExpect(status().isCreated())
				.andExpect(jsonPath("id", is(ACCOUNT_ID)))
				.andExpect(jsonPath("accountNumber", is(ACCOUNT_NUMBER)))
				.andExpect(jsonPath("orders", emptyIterable()))
				.andExpect(jsonPath("currency", equalTo(CURRENCY_PESO)))
				.andExpect(jsonPath("balance", equalTo(ZERO_BALANCE)))
				.andReturn();
	}

	private void thenResponseShouldReturnAccounts() throws Exception {
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(1l)))
				.andExpect(jsonPath("$[0].accountNumber", is(1001l)))
				.andExpect(jsonPath("$[0].currency", equalTo(CURRENCY_PESO)))
				.andExpect(jsonPath("$[1].id", is(2l)))
				.andExpect(jsonPath("$[1].accountNumber", is(1002l)))
				.andExpect(jsonPath("$[1].currency", equalTo(CURRENCY_PESO)))
				.andExpect(jsonPath("$[2].id", is(3l)))
				.andExpect(jsonPath("$[2].accountNumber", is(1003l)))
				.andExpect(jsonPath("$[2].currency", equalTo(CURRENCY_PESO)))
				.andReturn();
	}

	private void thenResponseShouldReturnBadRequest() throws Exception {
		response.andExpect(status().isBadRequest()).andReturn();
	}

	private void thenResponseShouldDeleteAccount() throws Exception {
		response.andExpect(status().isNoContent()).andReturn();
	}

	private void thenResponseShouldNotDeleteAccount() throws Exception {
		response.andExpect(status().isNotModified()).andReturn();
	}

	private void thenResponseShouldNotFoundAccount() throws Exception {
		response.andExpect(status().isNotFound()).andReturn();
	}

	private void thenResponseShouldReturnNewAccountWithOrder() throws Exception {
		response.andExpect(status().isCreated())
				.andExpect(jsonPath("id", is(ACCOUNT_ID)))
				.andExpect(jsonPath("accountNumber", is(ACCOUNT_NUMBER)))
				.andExpect(jsonPath("orders[0].id", is(1l)))
				.andExpect(jsonPath("orders[0].type", equalTo("CREDIT")))
				.andExpect(jsonPath("orders[0].description", equalTo("saraza")))
				.andExpect(jsonPath("orders[0].amount", equalTo(10)))
				.andExpect(jsonPath("currency", equalTo(CURRENCY_PESO)))
				.andExpect(jsonPath("balance", equalTo(10)))
				.andReturn();
	}

	private void thenResponseShouldReturnOrders() throws Exception {
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(1l)))
				.andExpect(jsonPath("$[0].type", equalTo("CREDIT")))
				.andExpect(jsonPath("$[0].description", equalTo("")))
				.andExpect(jsonPath("$[0].amount", greaterThan(0)))

				.andExpect(jsonPath("$[1].id", is(2l)))
				.andExpect(jsonPath("$[1].type", equalTo("CREDIT")))
				.andExpect(jsonPath("$[1].description", equalTo("")))
				.andExpect(jsonPath("$[1].amount", greaterThan(0)))

				.andExpect(jsonPath("$[2].id", is(3l)))
				.andExpect(jsonPath("$[2].type", equalTo("CREDIT")))
				.andExpect(jsonPath("$[2].description", equalTo("")))
				.andExpect(jsonPath("$[2].amount", greaterThan(0)))
				.andReturn();
	}

	private static Matcher<Integer> is(Long value) {
		return org.hamcrest.core.Is.is(value.intValue());
	}

	private Account createAccount(final Long id, final Long accountNumber) {
		Account account = new Account(accountNumber, PESO);
		account.addOrder(createRandomOrder());
		account.setId(id);
		return account;
	}

	private Order createRandomOrder() {
		return new Order(new Date(), CREDIT, "", BigDecimal.valueOf(RandomUtils.nextInt(0, 100000)));
	}

	private Order createRandomOrder(final Long id) {
		Order order = createRandomOrder();
		order.setId(id);
		return order;
	}
}


