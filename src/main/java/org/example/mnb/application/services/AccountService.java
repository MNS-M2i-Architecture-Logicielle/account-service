package org.example.mnb.application.services;

import org.example.mnb.adapters.out.JpaAccountRepository;
import org.example.mnb.application.ports.in.AccountUseCase;
import org.example.mnb.application.ports.out.AccountRepository;
import org.example.mnb.application.ports.out.ClientRepository;
import org.example.mnb.domain.Account;
import org.example.mnb.domain.Client;
import org.example.mnb.application.exceptions.ClientNotFoundException;
import org.example.mnb.application.exceptions.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements AccountUseCase {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public AccountService(JpaAccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Override
    public double getAccountBalance(Long id) {
        Account account = getAccountById(id);
        return account.getBalance();
    }
        
    @Override
    public Account createAccount(Long clientId, double initialBalance) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));

        Account account = new Account();
        account.setClient(client);
        account.setBalance(initialBalance);
        
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Long id, double newBalance) {
        Account account = getAccountById(id);
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}