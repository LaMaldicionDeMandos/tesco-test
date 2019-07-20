package coop.tecso.examen.model;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.joda.time.DateTime.now;
import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final BigDecimal CREDIT_ORDER_AMOUNT = BigDecimal.TEN;
    private static final BigDecimal DEBIT_ORDER_AMOUNT = BigDecimal.TEN;

    private static final BigDecimal PESO_VALID_DEBIT_ORDER_AMOUNT = BigDecimal.valueOf(1000);
    private static final BigDecimal PESO_INVALID_DEBIT_ORDER_AMOUNT = BigDecimal.valueOf(1001);
    private static final BigDecimal DOLLAR_VALID_DEBIT_ORDER_AMOUNT = BigDecimal.valueOf(300);
    private static final BigDecimal DOLLAR_INVALID_DEBIT_ORDER_AMOUNT = BigDecimal.valueOf(301);
    private static final BigDecimal EURO_VALID_DEBIT_ORDER_AMOUNT = BigDecimal.valueOf(150);
    private static final BigDecimal EURO_INVALID_DEBIT_ORDER_AMOUNT = BigDecimal.valueOf(151);

    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(1200);
    private static final BigDecimal INITIAL_BALANCE_MINUS_DEBIT_AMOUNT = BigDecimal.valueOf(1190);
    private static final Long ACCOUNT_NUMBER = 1l;
    private static final boolean VALID = true;
    private static final boolean INVALID = false;
    private static final String DESCRIPTION = "Description";
    private static final List<Order> ORDERS = Lists.newArrayList(
            new Order(now(), OrderType.CREDIT, "", BigDecimal.valueOf(1000)),
            new Order(now(), OrderType.DEBIT, "", BigDecimal.valueOf(200)),
            new Order(now(), OrderType.DEBIT, "", BigDecimal.valueOf(100)),
            new Order(now(), OrderType.CREDIT, "", BigDecimal.valueOf(500))
    );

    private Account account;
    private boolean isValid;
    private Order order;

    @Test
    public void AnAccountOnlyCanBeDeletedIfItHasNotOrders() {

    }

    @Test
    public void whenAddACreditOrderShouldIncrementBalance() {
        givenAnEmptyAccount();
        givenAnCreditOrderOf(CREDIT_ORDER_AMOUNT);

        whenAddOrderToAccount();

        thenAccountBalanceShouldBe(CREDIT_ORDER_AMOUNT);
    }

    @Test
    public void newAccountWithOrdersShouldCalculateBalanceCorrectly() {
        givenAnAccountWithOrders(ORDERS);

        thenAccountBalanceShouldBe(INITIAL_BALANCE);
    }

    @Test
    public void whenAddADebitOrderShouldDecrementBalance() {
        givenAnAccountWithOrders(ORDERS);
        givenAnDebitOrderOf(DEBIT_ORDER_AMOUNT);

        whenAddOrderToAccount();

        thenAddOrderShouldBe(VALID);
        thenAccountBalanceShouldBe(INITIAL_BALANCE_MINUS_DEBIT_AMOUNT);
    }

    @Test
    public void whenAddADebitOrderLessThanInPesoAccountLessThan1000() {
        givenAnEmptyAccount();
        givenAnDebitOrderOf(PESO_VALID_DEBIT_ORDER_AMOUNT);

        whenAddOrderToAccount();

        thenAddOrderShouldBe(VALID);
        thenAccountBalanceShouldBe(PESO_VALID_DEBIT_ORDER_AMOUNT.negate());
    }

    @Test
    public void whenAddADebitOrderGreaterThanInPesoAccountLessThan1000() {
        givenAnEmptyAccount();
        givenAnDebitOrderOf(PESO_INVALID_DEBIT_ORDER_AMOUNT);

        whenAddOrderToAccount();

        thenAddOrderShouldBe(INVALID);
        thenAccountBalanceShouldBe(BigDecimal.ZERO);
    }

    @Test
    public void whenAddADebitOrderLessThanInDollarAccountLessThan1000() {
        givenAnEmptyAccount(Currency.DOLLAR);
        givenAnDebitOrderOf(DOLLAR_VALID_DEBIT_ORDER_AMOUNT);

        whenAddOrderToAccount();

        thenAddOrderShouldBe(VALID);
        thenAccountBalanceShouldBe(DOLLAR_VALID_DEBIT_ORDER_AMOUNT.negate());
    }

    @Test
    public void whenAddADebitOrderGreaterThanInDollarAccountLessThan1000() {
        givenAnEmptyAccount(Currency.DOLLAR);
        givenAnDebitOrderOf(DOLLAR_INVALID_DEBIT_ORDER_AMOUNT);

        whenAddOrderToAccount();

        thenAddOrderShouldBe(INVALID);
        thenAccountBalanceShouldBe(BigDecimal.ZERO);
    }

    @Test
    public void whenAddADebitOrderLessThanInEuroAccountLessThan1000() {
        givenAnEmptyAccount(Currency.EURO);
        givenAnDebitOrderOf(EURO_VALID_DEBIT_ORDER_AMOUNT);

        whenAddOrderToAccount();

        thenAddOrderShouldBe(VALID);
        thenAccountBalanceShouldBe(EURO_VALID_DEBIT_ORDER_AMOUNT.negate());
    }

    @Test
    public void whenAddADebitOrderGreaterThanInEuroAccountLessThan1000() {
        givenAnEmptyAccount(Currency.EURO);
        givenAnDebitOrderOf(EURO_INVALID_DEBIT_ORDER_AMOUNT);

        whenAddOrderToAccount();

        thenAddOrderShouldBe(INVALID);
        thenAccountBalanceShouldBe(BigDecimal.ZERO);
    }

    private void givenAnAccountWithOrders(List<Order> orders) {
        account = new Account(ACCOUNT_NUMBER, orders, Currency.PESO);
    }

    private void givenAnCreditOrderOf(BigDecimal creditOrderAmount) {
        order = new Order(now(), OrderType.CREDIT, DESCRIPTION, creditOrderAmount);
    }

    private void givenAnDebitOrderOf(BigDecimal debitOrderAmount) {
        order = new Order(now(), OrderType.DEBIT, DESCRIPTION, debitOrderAmount);
    }

    private void givenAnEmptyAccount() {
        givenAnAccountWithOrders(Lists.newArrayList());
    }

    private void givenAnEmptyAccount(Currency currency) {
        givenAnAccountWithOrders(Lists.newArrayList(), currency);
    }

    private void givenAnAccountWithOrders(List<Order> orders, Currency currency) {
        account = new Account(ACCOUNT_NUMBER, orders, currency);
    }

    private void whenAddOrderToAccount() {
        isValid = account.addOrder(order);
    }

    private void thenAccountBalanceShouldBe(BigDecimal expectedBalance) {
        assertEquals(expectedBalance, account.getBalance());
    }

    private void thenAddOrderShouldBe(boolean valid) {
        assertEquals(valid, isValid);
    }


}
