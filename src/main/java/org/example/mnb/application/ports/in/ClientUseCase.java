package org.example.mnb.application.ports.in;

import org.example.mnb.domain.Client;

import java.util.List;

public interface ClientUseCase {
    List<Client> getAllClients();
    Client getClientById(Long id);
    Client createClient(String name, String mail);
    Client updateClient(Long id, Client updatedClient);
    void deleteClient(Long id);
}
