package org.example.mnb.services;

import org.example.mnb.dtos.AccountDTO;
import org.example.mnb.entities.Account;
import org.example.mnb.entities.Client;
import org.example.mnb.exceptions.AccountNotFoundException;
import org.example.mnb.exceptions.ClientNotFoundException;
import org.example.mnb.repositories.AccountRepository;
import org.example.mnb.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountService(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account getAccount(Long id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public void createAccount(AccountDTO dto) {
        Client client = clientRepository.findById(dto.getClient().getId())
                .orElseThrow(() -> new ClientNotFoundException(dto.getClient().getId()));
        
        Account account = new Account();
        account.setBalance(dto.getBalance());
        account.setClient(client);
        accountRepository.save(account);
    }
    
    public double getBalance(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return account.getBalance();
    }
}
