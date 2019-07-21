package coop.tecso.examen.repository;

import coop.tecso.examen.model.Account;
import coop.tecso.examen.model.Currency;
import coop.tecso.examen.model.Order;
import coop.tecso.examen.model.OrderType;
import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.joda.time.DateTime.now;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountRepositoryTest {

    private static final List<Order> ORDERS = Lists.newArrayList(
            new Order(now(), OrderType.CREDIT, "Order 4", BigDecimal.valueOf(1000)),
            new Order(new DateTime(2019, 6, 29, 19, 0), OrderType.DEBIT, "Order 2", BigDecimal.valueOf(200)),
            new Order(new DateTime(2019, 7, 29, 19, 0), OrderType.DEBIT, "Order 3", BigDecimal.valueOf(100)),
            new Order(new DateTime(2019, 5, 29, 19, 0), OrderType.CREDIT, "Order 1", BigDecimal.valueOf(500))
    );
    private static final Long ACCOUNT_NUMBER = 1l;

    @Autowired
    private AccountRepository repo;

    private Account originalAccount;
    private Account savedAccount;
    private Account foundAccount;

    @Test
    public void saveAndFindAccount() {
        givenAnAccount();

        whenSaveAccount();
        whenGetAccount();

        thenSavedAccountShouldHasId();
        thenSavedAccountShouldBeEqualToOriginal();
    }

    @Test(expected = JpaObjectRetrievalFailureException.class)
    public void deleteAnAccount() {
        givenAnAccount();

        whenSaveAccount();
        whenDeleteAccount();
        whenGetAccount();
    }

    private void givenAnAccount() {
        originalAccount = new Account(ACCOUNT_NUMBER, ORDERS, Currency.PESO);
    }

    private void whenSaveAccount() {
        savedAccount = repo.save(originalAccount);
    }

    private void whenGetAccount() {
        foundAccount = repo.getOne(savedAccount.getId());
    }

    private void whenDeleteAccount() {
        repo.delete(savedAccount);
    }

    private void thenSavedAccountShouldHasId() {
        assertNotNull(savedAccount.getId());
    }

    private void thenSavedAccountShouldBeEqualToOriginal() {
        assertEquals(originalAccount, foundAccount);
        assertEquals(originalAccount.getBalance(), foundAccount.getBalance());
        assertEquals(originalAccount.getOrders(), foundAccount.getOrders());
    }

    private void thenSavedAccountShouldHasOrderSortedByDate() {
        assertEquals("Order 4", foundAccount.getOrders().get(0).getDescription());
        assertEquals(BigDecimal.valueOf(1000), foundAccount.getOrders().get(0).getAmount());

        assertEquals("Order 3", foundAccount.getOrders().get(1).getDescription());
        assertEquals(BigDecimal.valueOf(100), foundAccount.getOrders().get(1).getAmount());

        assertEquals("Order 2", foundAccount.getOrders().get(2).getDescription());
        assertEquals(BigDecimal.valueOf(200), foundAccount.getOrders().get(2).getAmount());

        assertEquals("Order 1", foundAccount.getOrders().get(3).getDescription());
        assertEquals(BigDecimal.valueOf(500), foundAccount.getOrders().get(3).getAmount());
    }
}
