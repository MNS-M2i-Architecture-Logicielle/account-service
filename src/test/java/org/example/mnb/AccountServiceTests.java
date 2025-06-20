package org.example.mnb;

import org.example.mnb.dtos.AccountDTO;
import org.example.mnb.dtos.ClientDTO;
import org.example.mnb.entities.Account;
import org.example.mnb.entities.Client;
import org.example.mnb.exceptions.AccountNotFoundException;
import org.example.mnb.repositories.AccountRepository;
import org.example.mnb.repositories.ClientRepository;
import org.example.mnb.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTests {

    private AccountRepository accountRepository;
    private AccountService accountService;
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        clientRepository = mock(ClientRepository.class);
        accountService = new AccountService(accountRepository, clientRepository); // <-- FIXED: use real service, not mock
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
        Account account1 = new Account(1L, createMockClient(), 1000.0);
        Account account2 = new Account(2L, createMockClient(), 1500.0);

        when(accountRepository.findAll()).thenReturn(Arrays.asList(account1, account2));

        List<Account> result = accountService.getAllAccounts();

        assertEquals(2, result.size());
        assertEquals(1000.0, result.get(0).getBalance());
        assertEquals(1500.0, result.get(1).getBalance());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void getAccount_shouldReturnAccountWhenExists() {
        Account account = new Account(1L, createMockClient(), 2000.0);

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
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance(1000);

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        accountDTO.setClient(clientDTO);

        Client client = new Client();
        client.setId(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        accountService.createAccount(accountDTO);

        verify(accountRepository, times(1)).save(accountCaptor.capture());
        Account savedAccount = accountCaptor.getValue();

        assertEquals(1000, savedAccount.getBalance());
        assertEquals(client, savedAccount.getClient());
    }

    @Test
    void getBalance_shouldReturnBalanceWhenAccountExists() {
        Account account = new Account(1L, createMockClient(), 3000.0);

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
