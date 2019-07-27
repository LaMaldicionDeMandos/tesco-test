package coop.tecso.examen.controller;

import coop.tecso.examen.dto.AccountDto;
import coop.tecso.examen.exception.AccountNotFoundException;
import coop.tecso.examen.model.Currency;
import coop.tecso.examen.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/account", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class AccountController {

	private final AccountService service;

	@Autowired
	public AccountController(final AccountService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<AccountDto> newAccount(@RequestBody final AccountDto accountDto) {
		if (!validateAccount(accountDto)) return ResponseEntity.badRequest().build();
		AccountDto account = new AccountDto(service.newAccount(accountDto.getAccountNumber(), accountDto.getCurrency()));

		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable final Long id) {
		try {
			return service.deleteAccount(id)
					? ResponseEntity.ok().build()
					: ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		} catch (AccountNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private boolean validateAccount(AccountDto accountDto) {
		return validateAccountNumber(accountDto.getAccountNumber()) && validateCurrency(accountDto.getCurrency());
	}

	private boolean validateCurrency(Currency currency) {
		return nonNull(currency);
	}

	private boolean validateAccountNumber(Long accountNumber) {
		return nonNull(accountNumber);
	}


}
