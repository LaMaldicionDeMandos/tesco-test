package coop.tecso.examen.controller;

import coop.tecso.examen.controller.entitybuilder.AccountBuilder;
import coop.tecso.examen.dto.AccountDto;
import coop.tecso.examen.exception.AccountNotFoundException;
import coop.tecso.examen.service.AccountService;
import coop.tecso.examen.model.Account;
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

import static coop.tecso.examen.model.Currency.PESO;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class )
public class AccountControllerTest {

	private final static AccountBuilder accountBuilder = new AccountBuilder();
	public static final long ACCOUNT_NUMBER = 1l;
	public static final long ACCOUNT_ID = 1l;
	public static final String CURRENCY_PESO = "PESO";
	public static final int ZERO_BALANCE = 0;

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

	private void givenAnAccountServiceWithoutAccount() {
		when(accountService.deleteAccount(ACCOUNT_ID)).thenThrow(AccountNotFoundException.class);
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

	private void whenDeleteAccount() throws Exception {
		response = mvc.perform(delete(root + "/" + ACCOUNT_ID).contentType(APPLICATION_JSON));
	}

	private void whenPostNewAccountWithoutBody() throws Exception {
		response = mvc.perform(post(root).contentType(APPLICATION_JSON));
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

	private void thenResponseShouldReturnBadRequest() throws Exception {
		response.andExpect(status().isBadRequest()).andReturn();
	}

	private void thenResponseShouldDeleteAccount() throws Exception {
		response.andExpect(status().isOk()).andReturn();
	}

	private void thenResponseShouldNotDeleteAccount() throws Exception {
		response.andExpect(status().isNotModified()).andReturn();
	}

	private void thenResponseShouldNotFoundAccount() throws Exception {
		response.andExpect(status().isNotFound()).andReturn();
	}

	public static Matcher<Integer> is(Long value) {
		return org.hamcrest.core.Is.is(value.intValue());
	}
}


