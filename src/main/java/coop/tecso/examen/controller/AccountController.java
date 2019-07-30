package coop.tecso.examen.controller;

import coop.tecso.examen.dto.AccountDto;
import coop.tecso.examen.dto.OrderDto;
import coop.tecso.examen.exception.AccountNotFoundException;
import coop.tecso.examen.exception.InvalidOperationException;
import coop.tecso.examen.model.Currency;
import coop.tecso.examen.model.Order;
import coop.tecso.examen.model.OrderType;
import coop.tecso.examen.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;
import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*")
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
					? ResponseEntity.noContent().build()
					: ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		} catch (AccountNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public ResponseEntity<List<AccountDto>> list() {
		return ResponseEntity.ok(service.getAccounts().stream()
				.map(account -> new AccountDto(account)).collect(Collectors.toList()));
	}

	@PostMapping("{accountId}/order")
	public ResponseEntity<AccountDto> addOrder(@PathVariable final Long accountId, @RequestBody final OrderDto orderDto) {
		boolean isValid = validateOrder(orderDto);
		AccountDto account = null;
		try {
			account = new AccountDto(service.addOrder(accountId, new Order(new Date(), orderDto.getType(), orderDto.getDescription(), orderDto.getAmount())));
		}catch (InvalidOperationException e) {
			isValid = false;
		} finally {
			return isValid ? ResponseEntity.status(HttpStatus.CREATED).body(account) : ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("{accountId}/order")
	public ResponseEntity<List<OrderDto>> listOrders(@PathVariable final Long accountId) {
		try {
			return ResponseEntity.ok(service.listOrders(accountId).stream()
					.map(order -> new OrderDto(order)).collect(Collectors.toList()));
		} catch (AccountNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private boolean validateAccount(AccountDto accountDto) {
		return validateAccountNumber(accountDto.getAccountNumber()) && validateCurrency(accountDto.getCurrency());
	}

	private boolean validateOrder(OrderDto order) {
		return validateType(order.getType()) && validateAmount(order.getAmount());
	}

	private boolean validateAmount(BigDecimal amount) {
		return nonNull(amount) && amount.compareTo(ZERO) > 0;
	}

	private boolean validateType(OrderType type) {
		return nonNull(type);
	}

	private boolean validateCurrency(Currency currency) {
		return nonNull(currency);
	}

	private boolean validateAccountNumber(Long accountNumber) {
		return nonNull(accountNumber);
	}


}
