package org.example.mnb.adapters.out.account;

import org.example.mnb.application.ports.out.AccountRepository;
import org.example.mnb.domain.Account;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Component
public class AccountRepositoryAdapter implements AccountRepository {

    private final AccountPersistenceClient persistenceClient;

    @Autowired
    public AccountRepositoryAdapter(AccountPersistenceClient persistenceClient) {
        this.persistenceClient = persistenceClient;
    }

    @Override
    public List<Account> findAll() {
        return persistenceClient.getAllAccounts();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return persistenceClient.getAccountById(id);
    }

    @Override
    public Account save(Account account) {
        return persistenceClient.createAccount(account);
    }

    @Override
    public void deleteById(Long id) {
        persistenceClient.deleteById(id);
    }
}

