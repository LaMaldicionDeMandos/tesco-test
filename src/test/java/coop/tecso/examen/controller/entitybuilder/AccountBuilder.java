package coop.tecso.examen.controller.entitybuilder;

import coop.tecso.examen.dto.AccountDto;
import coop.tecso.examen.model.Currency;

public class AccountBuilder extends AbstractEntityBuilder<AccountDto> {
    private Long accountNumber;
    private Currency currency;

    public AccountBuilder withAccountNumber(final Long accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public AccountBuilder withCurrency(final Currency currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public AccountDto build() {
        return new AccountDto(null, accountNumber, currency, null);
    }

    @Override
    public AccountDto buildValid() {
        return new AccountDto(null, 1l, Currency.PESO, null);
    }
}
