package org.example.mnb;

import org.example.mnb.domain.Client;
import org.example.mnb.application.ports.out.ClientRepository;
import org.example.mnb.application.services.ClientService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceTests {

    private ClientRepository clientRepository;
    //private AccountRepository accountRepository;
    private ClientService clientService;

    /*@BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        accountRepository = mock(AccountRepository.class);
        clientService = new ClientService(clientRepository, accountRepository);
    }*/

    @Test
    void getAllClients_shouldReturnListOfClients() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("John Doe");
        client1.setMail("john@example.com");

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("Jane Doe");
        client2.setMail("jane@example.com");

        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        List<Client> result = clientService.getAllClients();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
        verify(clientRepository, times(1)).findAll();
    }

    /*@Test
    void getClient_shouldReturnClient_whenExists() {
        Client client = new Client();
        client.setId(1L);
        client.setName("Alice");
        client.setMail("alice@example.com");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client result = clientService.getClient(1L);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals("alice@example.com", result.getMail());
    }*/

    /*@Test
    void getClient_shouldThrow_whenClientNotFound() {
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClient(999L));
        verify(clientRepository, times(1)).findById(999L);
    }*/

    /*@Test
    void createClient_shouldSaveClientAndAccount() {
        // Préparation du DTO
        ClientDTO dto = new ClientDTO();
        dto.setName("Bob");
        dto.setMail("bob@example.com");

        // Simuler le comportement du repository
        Client savedClient = new Client();
        savedClient.setId(10L);
        savedClient.setName("Bob");
        savedClient.setMail("bob@example.com");

        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        // Appel de la méthode
        clientService.createClient(dto);

        // Vérifier que les méthodes save ont été appelées
        verify(clientRepository, times(1)).save(any(Client.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }*/

    /*@Test
    void createClient_shouldNotSaveClientAndAccount_whenDtoIsNull() {
        // Appel avec DTO null
        assertThrows(IllegalArgumentException.class, () -> clientService.createClient(null));

        verify(clientRepository, never()).save(any());
        verify(accountRepository, never()).save(any());
    }*/

    /*@Test
    void createClient_shouldNotSaveClientAndAccount_whenNameOrMailIsInvalid() {
        // DTO invalide
        ClientDTO dto = new ClientDTO();
        dto.setName(null);
        dto.setMail(null);

        assertThrows(IllegalArgumentException.class, () -> clientService.createClient(dto));

        verify(clientRepository, never()).save(any());
        verify(accountRepository, never()).save(any());
    }*/
}
