package coop.tecso.examen.service.impl;

import coop.tecso.examen.exception.AccountNotFoundException;
import coop.tecso.examen.exception.InvalidOperationException;
import coop.tecso.examen.model.Account;
import coop.tecso.examen.model.Currency;
import coop.tecso.examen.model.Order;
import coop.tecso.examen.repository.AccountRepository;
import coop.tecso.examen.repository.OrderRepository;
import coop.tecso.examen.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultAccountService implements AccountService {
    private final AccountRepository repository;
    private final OrderRepository orderRepository;

    @Autowired
    public DefaultAccountService(final AccountRepository repository, final OrderRepository orderRepository) {
        this.repository = repository;
        this.orderRepository = orderRepository;
    }

    DefaultAccountService() {
        this.repository = null;
        this.orderRepository = null;
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

    public Account addOrder(final Long accountId, Order order) {
        Account account = getAccount(accountId);
        if (!account.isValidOrder(order)) throw new InvalidOperationException();
        account.addOrder(orderRepository.save(order));
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
