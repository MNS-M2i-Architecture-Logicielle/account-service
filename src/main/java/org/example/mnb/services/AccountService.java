package org.example.mnb.services;

import org.example.mnb.entities.Account;
import org.example.mnb.exceptions.AccountNotFoundException;
import org.example.mnb.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
