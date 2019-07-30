package coop.tecso.examen.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super(String.format("Account with id %d not found", id));
    }
}
