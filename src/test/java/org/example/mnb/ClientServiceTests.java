package org.example.mnb;

import org.example.mnb.application.exceptions.ClientNotFoundException;
import org.example.mnb.application.ports.out.ClientRepository;
import org.example.mnb.application.services.AccountService;
import org.example.mnb.application.services.ClientService;
import org.example.mnb.domain.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTests {

    private ClientRepository clientRepository;
    private AccountService accountService;
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        accountService = mock(AccountService.class);
        clientService = new ClientService(clientRepository, accountService);
    }

    @Test
    void getAllClients_returnsAllClients() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("Alice");

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Bob");

        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        List<Client> clients = clientService.getAllClients();

        assertEquals(2, clients.size());
        assertEquals("Alice", clients.get(0).getName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void getClientById_existingId_returnsClient() {
        Client client = new Client();
        client.setId(1L);
        client.setName("Alice");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client result = clientService.getClientById(1L);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        verify(clientRepository).findById(1L);
    }

    @Test
    void getClientById_nonExistingId_throwsException() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClientById(99L));
        verify(clientRepository).findById(99L);
    }

    @Test
    void createClient_savesClientAndCreatesAccount() {
        Client client = new Client();
        client.setId(1L);
        client.setName("Alice");
        client.setMail("alice@test.com");

        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> {
            Client saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        Client created = clientService.createClient("Alice", "alice@test.com");

        assertNotNull(created.getId());
        assertEquals("Alice", created.getName());
        verify(clientRepository).save(any(Client.class));
        verify(accountService).createAccount(created.getId(), 0.0);
    }

    @Test
    void updateClient_existingId_updatesAndReturnsClient() {
        Client existing = new Client();
        existing.setId(1L);
        existing.setName("Alice");
        existing.setMail("alice@test.com");

        Client updated = new Client();
        updated.setName("Alicia");
        updated.setMail("alicia@test.com");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Client result = clientService.updateClient(1L, updated);

        assertEquals("Alicia", result.getName());
        assertEquals("alicia@test.com", result.getMail());
        verify(clientRepository).save(existing);
    }

    @Test
    void deleteClient_callsRepositoryDelete() {
        doNothing().when(clientRepository).deleteById(1L);

        clientService.deleteClient(1L);

        verify(clientRepository).deleteById(1L);
    }
}
