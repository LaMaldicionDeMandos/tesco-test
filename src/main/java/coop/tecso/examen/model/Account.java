package coop.tecso.examen.model;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

public class Account {
    private final Long accountNumber;
    private final List<Order> orders;
    private final Currency currency;
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
        return addOrderAmount(balance, order).negate().compareTo(currency.limit()) < 0;
    }

    public BigDecimal getBalance() {
        return balance;
    }


}
