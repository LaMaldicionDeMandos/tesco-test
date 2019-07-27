package coop.tecso.examen.dto;

import coop.tecso.examen.model.Order;
import coop.tecso.examen.model.OrderType;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDto {
    private final Long id;
    private final Date date;
    private final OrderType type;
    private final String description;
    private final BigDecimal amount;

    public OrderDto(Long id, Date date, OrderType type, String description, BigDecimal amount) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.description = description;
        this.amount = amount;
    }

    public OrderDto(Order order) {
        this.id = order.getId();
        this.date = order.getDate();
        this.type = order.getType();
        this.description = order.getDescription();
        this.amount = order.getAmount();
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public OrderType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
