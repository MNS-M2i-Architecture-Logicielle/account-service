package org.example.mnb;

import org.example.mnb.adapters.out.account.AccountPersistenceClient;
import org.example.mnb.adapters.out.client.ClientPersistenceClient;
import org.example.mnb.application.exceptions.AccountNotFoundException;
import org.example.mnb.application.exceptions.ClientNotFoundException;
import org.example.mnb.application.services.AccountService;
import org.example.mnb.domain.Account;
import org.example.mnb.domain.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTests {

    private AccountPersistenceClient accountPersistenceClient;
    private ClientPersistenceClient clientPersistenceClient;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountPersistenceClient = mock(AccountPersistenceClient.class);
        clientPersistenceClient = mock(ClientPersistenceClient.class);
        accountService = new AccountService(accountPersistenceClient, clientPersistenceClient);
    }

    @Test
    void getAllAccounts_ShouldReturnAccounts() {
        Account a1 = new Account(); a1.setId(1L); a1.setBalance(100);
        Account a2 = new Account(); a2.setId(2L); a2.setBalance(200);
        when(accountPersistenceClient.getAllAccounts()).thenReturn(Arrays.asList(a1, a2));

        List<Account> result = accountService.getAllAccounts();

        assertEquals(2, result.size());
        verify(accountPersistenceClient, times(1)).getAllAccounts();
    }

    @Test
    void getAccountById_WhenExists_ShouldReturnAccount() {
        Account acc = new Account(); acc.setId(1L); acc.setBalance(500);
        when(accountPersistenceClient.getAccountById(1L)).thenReturn(Optional.of(acc));

        Account result = accountService.getAccountById(1L);

        assertEquals(500, result.getBalance());
        verify(accountPersistenceClient, times(1)).getAccountById(1L);
    }

    @Test
    void getAccountById_WhenNotExists_ShouldThrowException() {
        when(accountPersistenceClient.getAccountById(99L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountById(99L));
    }

    @Test
    void getAccountBalance_ShouldReturnBalance() {
        Account acc = new Account(); acc.setId(1L); acc.setBalance(777);
        when(accountPersistenceClient.getAccountById(1L)).thenReturn(Optional.of(acc));

        double balance = accountService.getAccountBalance(1L);

        assertEquals(777, balance);
    }

    @Test
    void createAccount_ShouldCreateWithClient() {
        Client client = new Client(); client.setId(1L); client.setName("Alice");
        Account saved = new Account(); saved.setId(10L); saved.setBalance(1000); saved.setClient(client);

        when(clientPersistenceClient.getClientById(1L)).thenReturn(Optional.of(client));
        when(accountPersistenceClient.createAccount(any(Account.class))).thenReturn(saved);

        Account result = accountService.createAccount(1L, 1000);

        assertEquals(10L, result.getId());
        assertEquals(1000, result.getBalance());
        verify(clientPersistenceClient, times(1)).getClientById(1L);
        verify(accountPersistenceClient, times(1)).createAccount(any(Account.class));
    }

    @Test
    void createAccount_WhenClientNotFound_ShouldThrow() {
        when(clientPersistenceClient.getClientById(42L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> accountService.createAccount(42L, 500));
    }

    @Test
    void updateAccount_ShouldUpdateBalance() {
        Client client = new Client(); client.setId(1L);
        Account existing = new Account(); existing.setId(2L); existing.setBalance(300); existing.setClient(client);
        Account updated = new Account(); updated.setId(2L); updated.setBalance(999); updated.setClient(client);

        when(accountPersistenceClient.getAccountById(2L)).thenReturn(Optional.of(existing));
        when(accountPersistenceClient.createAccount(existing)).thenReturn(updated);

        Account result = accountService.updateAccount(2L, 999);

        assertEquals(999, result.getBalance());
        verify(accountPersistenceClient, times(1)).getAccountById(2L);
        verify(accountPersistenceClient, times(1)).createAccount(existing);
    }

    @Test
    void deleteAccount_ShouldCallRepository() {
        accountService.deleteAccount(5L);

        verify(accountPersistenceClient, times(1)).deleteById(5L);
    }
}
