package coop.tecso.examen.service;

import coop.tecso.examen.model.Account;
import coop.tecso.examen.model.Currency;
import coop.tecso.examen.model.Order;

import java.util.List;

public interface AccountService {
    Account newAccount(Long accountNumber, Currency currency);
    boolean deleteAccount(Long id);
    List<Account> getAccounts();
    Account addOrder(Long accountId, Order order);
    List<Order> listOrders(Long accountId);
}
