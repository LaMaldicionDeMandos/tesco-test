package coop.tecso.examen.service.impl;

import coop.tecso.examen.exception.AccountNotFoundException;
import coop.tecso.examen.exception.InvalidOperationException;
import coop.tecso.examen.model.Account;
import coop.tecso.examen.model.Currency;
import coop.tecso.examen.model.Order;
import coop.tecso.examen.repository.AccountRepository;
import coop.tecso.examen.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultAccountService implements AccountService {
    private final AccountRepository repository;

    @Autowired
    public DefaultAccountService(final AccountRepository repository) {
        this.repository = repository;
    }

    DefaultAccountService() {
        this.repository = null;
    }

    public Account newAccount(final Long accountNumber, final Currency currency) {
        Account account = new Account(accountNumber, currency);
        return repository.save(account);
    }

    public boolean deleteAccount(final Long id) {
        Account account = getAccount(id);
        boolean canDelete = !account.hasOrders();
        if (canDelete) deleteAccount(account);
        return canDelete;
    }

    public List<Account> getAccounts() {
        return repository.findAll();
    }

    public Account addOrder(final Long accountId, final Order order) {
        Account account = getAccount(accountId);
        if (!account.addOrder(order)) throw new InvalidOperationException();
        repository.save(account);
        return account;
    }

    public List<Order> listOrders(final Long accountId) {
        Account account = getAccount(accountId);
        return account.getOrders();
    }

    private Account getAccount(final Long accountId) {
        try {
            return repository.getOne(accountId);
        } catch (NullPointerException e) {
            throw new AccountNotFoundException(accountId);
        }
    }

    private void deleteAccount(final Account account) {
        repository.delete(account);
    }
}
