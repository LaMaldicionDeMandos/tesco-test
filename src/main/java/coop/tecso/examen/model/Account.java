package coop.tecso.examen.model;

import com.google.common.collect.Lists;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class Account extends AbstractPersistentObject {
    @Basic
    private final Long accountNumber;

    @OneToMany(fetch = FetchType.EAGER)
    private final List<Order> orders;

    @Enumerated
    private final Currency currency;

    @Basic
    private BigDecimal balance;

    public Account(Long accountNumber, List<Order> orders, Currency currency) {
        this.orders = Lists.newArrayList();
        this.accountNumber = accountNumber;
        this.currency = currency;
        balance = BigDecimal.ZERO;
        orders.stream().forEach(this::addOrder);
    }

    private static BigDecimal addOrderAmount(BigDecimal partialValue, Order order) {
        return partialValue.add(order.signedAmount());
    }


    public boolean addOrder(final Order order) {
        Boolean isValid = isValidOrder(order);
        if (isValid) {
            balance = addOrderAmount(balance, order);
            orders.add(order);
        }
        return isValid;
    }

    private Boolean isValidOrder(Order order) {
        return addOrderAmount(balance, order).negate().compareTo(currency.limit()) <= 0;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public List<Order> getOrders() {
        return Lists.newArrayList(orders);
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) &&
                currency == account.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountNumber, currency);
    }
}
