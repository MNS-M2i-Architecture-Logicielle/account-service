package org.example.mnb.adapters.in;

import org.example.mnb.application.ports.in.AccountUseCase;
import org.example.mnb.domain.Account;
import org.example.mnb.adapters.in.dto.request.AccountCreationRequest;
import org.example.mnb.adapters.in.dto.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountUseCase accountUseCase;

    @Autowired
    public AccountController(AccountUseCase accountUseCase) {
        this.accountUseCase = accountUseCase;
    }

    @GetMapping
    public List<AccountResponse> getAllAccounts() {
        return accountUseCase.getAllAccounts().stream()
                .map(this::mapToAccountResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountResponse getAccountById(@PathVariable Long id) {
        Account account = accountUseCase.getAccountById(id);
        return mapToAccountResponse(account);
    }
    
    @GetMapping("/balance/{id}")
    public double getAccountBalance(@PathVariable Long id) {
        return accountUseCase.getAccountBalance(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@RequestBody AccountCreationRequest request) {
        Account createdAccount = accountUseCase.createAccount(request.getClientId(), request.getInitialBalance());
        return mapToAccountResponse(createdAccount);
    }

    @PutMapping("/{id}")
    public AccountResponse updateAccount(@PathVariable Long id, @RequestBody double newBalance) {
        Account updatedAccount = accountUseCase.updateAccount(id, newBalance);
        return mapToAccountResponse(updatedAccount);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long id) {
        accountUseCase.deleteAccount(id);
    }

    private AccountResponse mapToAccountResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getBalance(),
                account.getClient().getId()
        );
    }
}