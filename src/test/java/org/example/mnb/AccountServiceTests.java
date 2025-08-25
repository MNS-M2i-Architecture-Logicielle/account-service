package org.example.mnb;

import org.example.mnb.application.exceptions.AccountNotFoundException;
import org.example.mnb.application.exceptions.ClientNotFoundException;
import org.example.mnb.application.ports.out.AccountRepository;
import org.example.mnb.application.ports.out.ClientRepository;
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

    private AccountRepository accountRepository;
    private ClientRepository clientRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        clientRepository = mock(ClientRepository.class);
        accountService = new AccountService(accountRepository, clientRepository);
    }

    @Test
    void getAllAccounts_ShouldReturnAccounts() {
        Account a1 = new Account(); a1.setId(1L); a1.setBalance(100);
        Account a2 = new Account(); a2.setId(2L); a2.setBalance(200);

        when(accountRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        List<Account> result = accountService.getAllAccounts();

        assertEquals(2, result.size());
        assertEquals(100, result.get(0).getBalance());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void getAccountById_WhenExists_ShouldReturnAccount() {
        Account acc = new Account(); acc.setId(1L); acc.setBalance(500);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));

        Account result = accountService.getAccountById(1L);

        assertEquals(500, result.getBalance());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void getAccountById_WhenNotExists_ShouldThrowException() {
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountById(99L));
    }

    @Test
    void getAccountBalance_ShouldReturnBalance() {
        Account acc = new Account(); acc.setId(1L); acc.setBalance(777);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));

        double balance = accountService.getAccountBalance(1L);

        assertEquals(777, balance);
    }

    @Test
    void createAccount_ShouldSaveWithClient() {
        Client client = new Client(); client.setId(1L); client.setName("Alice");
        Account acc = new Account(); acc.setId(10L); acc.setBalance(1000); acc.setClient(client);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(accountRepository.save(any(Account.class))).thenReturn(acc);

        Account result = accountService.createAccount(1L, 1000);

        assertEquals(10L, result.getId());
        assertEquals(1000, result.getBalance());
        assertEquals(1L, result.getClient().getId());
        verify(clientRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void createAccount_WhenClientNotFound_ShouldThrow() {
        when(clientRepository.findById(42L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> accountService.createAccount(42L, 500));
    }

    @Test
    void updateAccount_ShouldUpdateBalance() {
        Client client = new Client(); client.setId(1L);
        Account existing = new Account(); existing.setId(2L); existing.setBalance(300); existing.setClient(client);
        Account updated = new Account(); updated.setId(2L); updated.setBalance(999); updated.setClient(client);

        when(accountRepository.findById(2L)).thenReturn(Optional.of(existing));
        when(accountRepository.save(existing)).thenReturn(updated);

        Account result = accountService.updateAccount(2L, 999);

        assertEquals(999, result.getBalance());
        verify(accountRepository, times(1)).findById(2L);
        verify(accountRepository, times(1)).save(existing);
    }

    @Test
    void deleteAccount_ShouldCallRepository() {
        accountService.deleteAccount(5L);

        verify(accountRepository, times(1)).deleteById(5L);
    }
}
