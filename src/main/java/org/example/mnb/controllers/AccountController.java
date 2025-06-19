package org.example.mnb.controllers;

import org.example.mnb.dtos.AccountDTO;
import org.example.mnb.entities.Account;
import org.example.mnb.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing bank accounts.
 *
 * Exposes endpoints under the base path "/api/account" to:
 * - retrieve account information,
 * - create or update accounts,
 * - manage balances.
 */
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


    /**
     * Retrieves the account details for the given ID.
     *
     * @param id the ID of the account
     * @return the account information
     */
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id){
        return accountService.getAccount(id);
    }
    
    @PostMapping("/new")
    public String createAccount(@RequestBody AccountDTO account){
        accountService.createAccount(account);
        return "Account successfully created !";
    }
    
    @GetMapping("/balance/{id}")
    public String getAccountBalance(@PathVariable Long id){
        return "Balance of the account n° " + id + " " + accountService.getBalance(id) + "€";
    }
}