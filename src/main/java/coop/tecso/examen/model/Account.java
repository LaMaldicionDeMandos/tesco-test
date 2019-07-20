package coop.tecso.examen.model;

import java.math.BigDecimal;
import java.util.List;

public class Account {
    private final Long accountNumber;
    private final List<Order> orders;
    private final Currency currency;
    private final BigDecimal balance;

    public Account(Long accountNumber, List<Order> orders, Currency currency, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.orders = orders;
        this.currency = currency;
        this.balance = balance;
    }


}
