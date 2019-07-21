package coop.tecso.examen.model;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity(name = "order_table")
public class Order extends AbstractPersistentObject {

    @Temporal(TemporalType.TIMESTAMP)
    private final Date date;

    @Basic
    private final OrderType type;

    @Basic
    private final String description;

    @Basic
    private final BigDecimal amount;

    public Order(Date date, OrderType type, String description, BigDecimal amount) {
        this.date = date;
        this.type = type;
        this.description = description;
        this.amount = amount;
    }

    public Order(DateTime date, OrderType type, String description, BigDecimal amount) {
        this.date = date.toDate();
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

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal signedAmount() {
        return OrderType.DEBIT == type ? amount.negate() : amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(date, order.date) &&
                type == order.type &&
                Objects.equals(description, order.description) &&
                Objects.equals(amount, order.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, type, description, amount);
    }
}
