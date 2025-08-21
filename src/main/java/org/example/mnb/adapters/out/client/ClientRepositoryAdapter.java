package org.example.mnb.adapters.out.client;

import org.example.mnb.application.ports.out.ClientRepository;
import org.example.mnb.domain.Client;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClientRepositoryAdapter implements ClientRepository {

    private final ClientPersistenceClient clientPersistenceClient;

    public ClientRepositoryAdapter(ClientPersistenceClient clientPersistenceClient) {
        this.clientPersistenceClient = clientPersistenceClient;
    }

    @Override
    public List<Client> findAll() {
        return clientPersistenceClient.getAllClients();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientPersistenceClient.getClientById(id);
    }

    @Override
    public Client save(Client client) {
        if (client.getId() == null) {
            return clientPersistenceClient.createClient(client);
        } else {
            return clientPersistenceClient.updateClient(client.getId(), client);
        }
    }

    @Override
    public void deleteById(Long id) {
        clientPersistenceClient.deleteClient(id);
    }
}

