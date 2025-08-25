package org.example.mnb;

import org.example.mnb.adapters.out.client.ClientPersistenceClient;
import org.example.mnb.application.exceptions.ClientNotFoundException;
import org.example.mnb.application.services.ClientService;
import org.example.mnb.domain.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTests {

    private ClientPersistenceClient clientPersistenceClient;
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientPersistenceClient = mock(ClientPersistenceClient.class);
        clientService = new ClientService(clientPersistenceClient);
    }

    @Test
    void getAllClients_ShouldReturnClients() {
        // Arrange
        Client c1 = new Client();
        c1.setId(1L);
        c1.setName("Alice");

        Client c2 = new Client();
        c2.setId(2L);
        c2.setName("Bob");

        when(clientPersistenceClient.getAllClients()).thenReturn(Arrays.asList(c1, c2));

        // Act
        List<Client> result = clientService.getAllClients();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        verify(clientPersistenceClient, times(1)).getAllClients();
    }

    @Test
    void getClientById_WhenExists_ShouldReturnClient() {
        // Arrange
        Client client = new Client();
        client.setId(1L);
        client.setName("Charlie");

        when(clientPersistenceClient.getClientById(1L)).thenReturn(Optional.of(client));

        // Act
        Client result = clientService.getClientById(1L);

        // Assert
        assertEquals("Charlie", result.getName());
        verify(clientPersistenceClient, times(1)).getClientById(1L);
    }

    @Test
    void getClientById_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(clientPersistenceClient.getClientById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ClientNotFoundException.class, () -> clientService.getClientById(99L));
        verify(clientPersistenceClient, times(1)).getClientById(99L);
    }

    @Test
    void createClient_ShouldSaveClientAndCreateAccount() {
        // Arrange
        Client client = new Client();
        client.setId(1L);
        client.setName("Daisy");
        client.setMail("daisy@mail.com");

        when(clientPersistenceClient.createClient(any(Client.class))).thenReturn(client);

        // Act
        Client result = clientService.createClient("Daisy", "daisy@mail.com", "root");

        // Assert
        assertNotNull(result);
        assertEquals("Daisy", result.getName());

        // Capture client argument
        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientPersistenceClient, times(1)).createClient(clientCaptor.capture());
        assertEquals("Daisy", clientCaptor.getValue().getName(), client.getPassword());
    }

    @Test
    void updateClient_ShouldUpdateAndReturnUpdatedClient() {
        // Arrange
        Client existing = new Client();
        existing.setId(1L);
        existing.setName("OldName");
        existing.setMail("old@mail.com");

        Client updated = new Client();
        updated.setName("NewName");
        updated.setMail("new@mail.com");

        when(clientPersistenceClient.getClientById(1L)).thenReturn(Optional.of(existing));
        when(clientPersistenceClient.createClient(any(Client.class))).thenReturn(existing);

        // Act
        Client result = clientService.updateClient(1L, updated);

        // Assert
        assertEquals("NewName", result.getName());
        assertEquals("new@mail.com", result.getMail());

        verify(clientPersistenceClient, times(1)).getClientById(1L);
        verify(clientPersistenceClient, times(1)).createClient(existing);
    }

    @Test
    void deleteClient_ShouldCallDelete() {
        // Act
        clientService.deleteClient(1L);

        // Assert
        verify(clientPersistenceClient, times(1)).deleteClient(1L);
    }
}
