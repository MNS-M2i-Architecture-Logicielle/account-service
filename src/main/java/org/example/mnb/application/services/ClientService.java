package org.example.mnb.application.services;

import org.example.mnb.adapters.out.account.AccountPersistenceClient;
import org.example.mnb.adapters.out.client.ClientPersistenceClient;
import org.example.mnb.application.ports.in.ClientUseCase;
import org.example.mnb.domain.Account;
import org.example.mnb.domain.Client;
import org.example.mnb.application.exceptions.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements ClientUseCase {
    
    private final ClientPersistenceClient clientRepository;
    private final AccountPersistenceClient accountService;

    @Autowired
    public ClientService(ClientPersistenceClient clientRepository, AccountPersistenceClient accountService) {
        this.clientRepository = clientRepository;
        this.accountService = accountService;
    }
    
    @Override
    public List<Client> getAllClients(){
        return clientRepository.getAllClients();
    }
    
    @Override
    public Client getClientById(Long id){
        return clientRepository.getClientById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }
    
    @Override
    public Client createClient(String name, String mail) {
        Client client = new Client();
        client.setName(name);
        client.setMail(mail);
        clientRepository.createClient(client);

        // Création d’un compte lié au nouveau client
        Account account = new Account();
        account.setClient(client);
        accountService.createAccount(account);

        return client;
    }
    
    @Override
    public Client updateClient(Long id, Client updatedClient) {
        Client client = getClientById(id);
        client.setName(updatedClient.getName());
        client.setMail(updatedClient.getMail());

        return clientRepository.createClient(client);
    }
    
    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteClient(id);
    }
}
