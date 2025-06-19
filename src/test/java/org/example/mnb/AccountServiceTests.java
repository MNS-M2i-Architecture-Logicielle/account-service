package org.example.mnb;

import org.example.mnb.entities.Account;
import org.example.mnb.entities.Client;
import org.example.mnb.exceptions.AccountNotFoundException;
import org.example.mnb.repositories.AccountRepository;
import org.example.mnb.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTests {

    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    private Client createMockClient() {
        Client client = new Client();
        client.setId(1L);
        client.setName("John Doe");
        client.setMail("john@example.com");
        return client;
    }

    @Test
    void getAllAccounts_shouldReturnListOfAccounts() {
        Client client = createMockClient();
        Account account1 = new Account(1L, client, 1000.0);
        Account account2 = new Account(2L, client, 1500.0);

        when(accountRepository.findAll()).thenReturn(Arrays.asList(account1, account2));

        List<Account> result = accountService.getAllAccounts();

        assertEquals(2, result.size());
        assertEquals(1000.0, result.get(0).getBalance());
        assertEquals(1500.0, result.get(1).getBalance());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void getAccount_shouldReturnAccountWhenExists() {
        Client client = createMockClient();
        Account account = new Account(1L, client, 2000.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account result = accountService.getAccount(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(2000.0, result.getBalance());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void getAccount_shouldThrowWhenNotFound() {
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(999L));
        verify(accountRepository, times(1)).findById(999L);
    }

    @Test
    void createAccount_shouldSaveAccount() {
        Client client = createMockClient();
        Account newAccount = new Account(null, client, 500.0);

        accountService.createAccount(newAccount);

        verify(accountRepository, times(1)).save(newAccount);
    }

    @Test
    void getBalance_shouldReturnBalanceWhenAccountExists() {
        Client client = createMockClient();
        Account account = new Account(1L, client, 3000.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        double balance = accountService.getBalance(1L);

        assertEquals(3000.0, balance);
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void getBalance_shouldThrowWhenAccountNotFound() {
        when(accountRepository.findById(555L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getBalance(555L));
        verify(accountRepository, times(1)).findById(555L);
    }
}
