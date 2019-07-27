package coop.tecso.examen.service;

import coop.tecso.examen.exception.InvalidOperationException;
import coop.tecso.examen.model.Account;
import coop.tecso.examen.model.Order;
import coop.tecso.examen.model.OrderType;
import coop.tecso.examen.repository.AccountRepository;
import coop.tecso.examen.service.impl.DefaultAccountService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Date;

import static coop.tecso.examen.model.Currency.PESO;
import static java.math.BigDecimal.TEN;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultAccountServiceTest {

    private static final Long ACCOUNT_NUMBER = 1l;
    private static final Long ACCOUNT_ID = 1l;
    private static final Order ORDER = new Order(new Date(), OrderType.CREDIT, "", TEN);
    private static final Order INVALID_ORDER = new Order(new Date(), OrderType.DEBIT, "", BigDecimal.valueOf(10000));

    @Mock
    private AccountRepository repo;
    private Account account;
    private DefaultAccountService service;
    private boolean result;



    @Test
    public void deleteAccountWithoutOrders() {
        givenANewAccount();
        givenAnAccountService();

        whenDeleteAccount();

        thenAccountShouldBeDeleted();
        thenAccountRepositoryShouldDeleteAccount();
    }

    @Test
    public void deleteAccountWithOrders() {
        givenANewAccountWithOrders();
        givenAnAccountService();

        whenDeleteAccount();

        thenAccountShouldNotBeDeleted();
        thenAccountRepositoryShouldNotDeleteAccount();
    }

    @Test
    public void addingOrderToAccountShouldModifyIt() {
        givenANewAccount();
        givenAnAccountService();

        whenAddOrder(ORDER);

        thenAccountShouldBeModified();
        thenAccountShouldBeSaved();
    }

    @Test(expected = InvalidOperationException.class)
    public void addingInvalidOrderToAccountShouldRejectTransaction() {
        givenANewAccount();
        givenAnAccountService();

        whenAddOrder(INVALID_ORDER);
    }

    private void givenAnAccountService() {
        service = new DefaultAccountService(repo);
    }

    private void givenANewAccountWithOrders() {
        account = new Account(ACCOUNT_NUMBER, Lists.newArrayList(ORDER), PESO);
        when(repo.getOne(anyLong())).thenReturn(account);
    }

    private void givenANewAccount() {
        account = new Account(ACCOUNT_NUMBER, PESO);
        when(repo.getOne(anyLong())).thenReturn(account);
    }

    private void whenDeleteAccount() {
        result = service.deleteAccount(ACCOUNT_ID);
    }

    private void whenAddOrder(Order order) {
        account = service.addOrder(ACCOUNT_ID, order);
    }

    private void thenAccountShouldBeDeleted() {
        assertTrue(result);
    }

    private void thenAccountShouldNotBeDeleted() {
        assertFalse(result);
    }

    private void thenAccountRepositoryShouldDeleteAccount() {
        verify(repo).delete(any());
    }

    private void thenAccountRepositoryShouldNotDeleteAccount() {
        verify(repo, never()).delete(any());
    }

    private void thenAccountShouldBeModified() {
        assertEquals(TEN, account.getBalance());
    }

    private void thenAccountShouldBeSaved() {
        verify(repo).save(account);
    }
}
