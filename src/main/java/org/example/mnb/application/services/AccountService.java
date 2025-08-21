package org.example.mnb.application.services;

import org.example.mnb.adapters.out.account.AccountPersistenceClient;
import org.example.mnb.adapters.out.client.ClientPersistenceClient;
import org.example.mnb.application.ports.in.AccountUseCase;
import org.example.mnb.domain.Account;
import org.example.mnb.domain.Client;
import org.example.mnb.application.exceptions.ClientNotFoundException;
import org.example.mnb.application.exceptions.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements AccountUseCase {

    private final AccountPersistenceClient accountRepository;
    private final ClientPersistenceClient clientRepository;

    @Autowired
    public AccountService(AccountPersistenceClient accountRepository, ClientPersistenceClient clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.getAccountById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Override
    public double getAccountBalance(Long id) {
        return getAccountById(id).getBalance();
    }
        
    @Override
    public Account createAccount(Long clientId, double initialBalance) {
        Client client = clientRepository.getClientById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));

        Account account = new Account();
        account.setClient(client);
        account.setBalance(initialBalance);
        
        return accountRepository.createAccount(account);
    }

    @Override
    public Account updateAccount(Long id, double newBalance) {
        Account account = getAccountById(id);
        account.setBalance(newBalance);
        return accountRepository.createAccount(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}