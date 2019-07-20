package coop.tecso.examen.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class Order {
    private final DateTime date;
    private final OrderType type;
    private final String description;
    private final BigDecimal amount;

    public Order(DateTime date, OrderType type, String description, BigDecimal amount) {
        this.date = date;
        this.type = type;
        this.description = description;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public OrderType getType() {
        return type;
    }

    public BigDecimal signedAmount() {
        return OrderType.DEBIT == type ? amount.negate() : amount;
    }
}
