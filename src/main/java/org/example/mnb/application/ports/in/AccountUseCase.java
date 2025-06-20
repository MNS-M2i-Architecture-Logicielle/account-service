package org.example.mnb.application.ports.in;

import org.example.mnb.domain.Account;

import java.util.List;

public interface AccountUseCase {
    List<Account> getAllAccounts();
    Account getAccountById(Long id);
    double getAccountBalance(Long id);
    Account createAccount(Long clientId, double initialBalance);
    Account updateAccount(Long id, double newBalance);
    void deleteAccount(Long id);
}
