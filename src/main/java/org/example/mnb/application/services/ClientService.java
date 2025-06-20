package org.example.mnb.application.services;

import org.example.mnb.application.ports.in.ClientUseCase;
import org.example.mnb.domain.Client;
import org.example.mnb.application.exceptions.ClientNotFoundException;
import org.example.mnb.application.ports.out.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements ClientUseCase {
    
    private final ClientRepository clientRepository;
    private final AccountService accountService;

    @Autowired
    public ClientService(ClientRepository clientRepository, AccountService accountService) {
        this.clientRepository = clientRepository;
        this.accountService = accountService;
    }
    
    @Override
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }
    
    @Override
    public Client getClientById(Long id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }
    
    @Override
    public Client createClient(String name, String mail) {
        Client client = new Client();
        client.setName(name);
        client.setMail(mail);
        clientRepository.save(client);

        // Création d’un compte lié au nouveau client
        accountService.createAccount(client.getId(), 0.0);

        return client;
    }
    
    @Override
    public Client updateClient(Long id, Client updatedClient) {
        Client client = getClientById(id);
        client.setName(updatedClient.getName());
        client.setMail(updatedClient.getMail());
        clientRepository.save(client);

        return client;
    }
    
    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
