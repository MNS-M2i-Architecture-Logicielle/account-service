package org.example.mnb;

import org.example.mnb.application.exceptions.ClientNotFoundException;
import org.example.mnb.application.ports.out.ClientRepository;
import org.example.mnb.application.services.AccountService;
import org.example.mnb.application.services.ClientService;
import org.example.mnb.domain.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTests {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private ClientService clientService;

    @Test
    void getAllClients_shouldReturnListOfClients() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("Alice");
        client1.setMail("alice@example.com");

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Bob");
        client2.setMail("bob@example.com");

        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        List<Client> result = clientService.getAllClients();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void getClientById_shouldReturnClient_whenExists() {
        Client client = new Client();
        client.setId(1L);
        client.setName("Charlie");
        client.setMail("charlie@example.com");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client result = clientService.getClientById(1L);

        assertNotNull(result);
        assertEquals("Charlie", result.getName());
        assertEquals("charlie@example.com", result.getMail());
    }

    @Test
    void getClientById_shouldThrow_whenClientDoesNotExist() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClientById(99L));
        verify(clientRepository).findById(99L);
    }

    @Test
    void createClient_shouldSaveClientAndCreateAccount() {
        // Arrange
        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);

        Client savedClient = new Client();
        savedClient.setId(5L);
        savedClient.setName("Dana");
        savedClient.setMail("dana@example.com");

        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> {
            Client c = invocation.getArgument(0);
            c.setId(5L); // Simulate generated ID
            return c;
        });

        // Act
        Client result = clientService.createClient("Dana", "dana@example.com");

        // Assert
        verify(clientRepository).save(clientCaptor.capture());
        verify(accountService).createAccount(eq(5L), eq(0.0));

        Client captured = clientCaptor.getValue();
        assertEquals("Dana", captured.getName());
        assertEquals("dana@example.com", captured.getMail());

        assertNotNull(result);
        assertEquals("Dana", result.getName());
        assertEquals("dana@example.com", result.getMail());
        assertEquals(5L, result.getId());
    }

    @Test
    void updateClient_shouldUpdateClientInfo() {
        // Arrange
        Client existing = new Client();
        existing.setId(10L);
        existing.setName("Old Name");
        existing.setMail("old@mail.com");

        Client updated = new Client();
        updated.setName("New Name");
        updated.setMail("new@mail.com");

        when(clientRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(clientRepository.save(any(Client.class))).thenReturn(existing);

        // Act
        Client result = clientService.updateClient(10L, updated);

        // Assert
        verify(clientRepository).save(existing);
        assertEquals("New Name", result.getName());
        assertEquals("new@mail.com", result.getMail());
    }

//    Client ne se supprime pas tant qu'il a un compte, pas encore codé
//    @Test
//    void deleteClient_shouldCallRepositoryDeleteById() {
//        clientService.deleteClient(123L);
//        verify(clientRepository, times(1)).deleteById(123L);
//    }
}
