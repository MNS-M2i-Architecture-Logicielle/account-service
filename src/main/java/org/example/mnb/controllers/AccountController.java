package org.example.mnb.controllers;

import org.example.mnb.dtos.AccountDTO;
import org.example.mnb.entities.Account;
import org.example.mnb.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }
    
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id){
        return accountService.getAccount(id);
    }
    
    @PostMapping("/new")
    public void createAccount(@RequestBody AccountDTO account){
        accountService.createAccount(account);
    }
    
    @GetMapping("/balance/{id}")
    public String getAccountBalance(@PathVariable Long id){
        return "Balance of the account n° " + id + " " + accountService.getBalance(id) + "€";
    }
}