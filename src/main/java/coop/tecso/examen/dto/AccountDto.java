package coop.tecso.examen.dto;

import com.google.common.collect.Lists;
import coop.tecso.examen.model.Account;
import coop.tecso.examen.model.Currency;
import coop.tecso.examen.model.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDto {
    private final Long id;
    private final Long accountNumber;
    private final Currency currency;
    private final BigDecimal balance;

    public AccountDto(Long id, Long accountNumber, Currency currency, BigDecimal balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.balance = balance;
    }

    public AccountDto(final Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.currency = account.getCurrency();
        this.balance = account.getBalance();
    }

    public Long getId() {
        return id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
