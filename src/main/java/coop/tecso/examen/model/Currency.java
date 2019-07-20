package coop.tecso.examen.model;

import java.math.BigDecimal;

public enum Currency {
    PESO {
        @Override
        public BigDecimal limit() {
            return BigDecimal.valueOf(1000);
        }
    },
    DOLLAR {
        @Override
        public BigDecimal limit() {
            return BigDecimal.valueOf(300);
        }
    },
    EURO {
        @Override
        public BigDecimal limit() {
            return BigDecimal.valueOf(150);
        }
    };

    public abstract BigDecimal limit();
}
