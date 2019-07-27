package coop.tecso.examen.controller.entitybuilder;

import coop.tecso.examen.dto.OrderDto;
import coop.tecso.examen.model.OrderType;

import java.math.BigDecimal;

import static coop.tecso.examen.model.OrderType.CREDIT;
import static java.math.BigDecimal.TEN;

public class OrderBuilder extends AbstractEntityBuilder<OrderDto> {
    private OrderType type;
    private String description;
    private BigDecimal amount;

    public OrderBuilder withType(final OrderType type) {
        this.type = type;
        return this;
    }

    public OrderBuilder withDescription(final String description) {
        this.description = description;
        return this;
    }

    public OrderBuilder withAmount(final BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public OrderDto build() {
        return new OrderDto(null, null, type, description, amount);
    }

    @Override
    public OrderDto buildValid() {
        return new OrderDto(null, null, CREDIT, "", TEN);
    }
}
