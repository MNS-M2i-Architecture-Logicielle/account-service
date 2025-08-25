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
    private final AccountPersistenceClient accountRepository;

    @Autowired
    public ClientService(ClientPersistenceClient clientRepository, AccountPersistenceClient accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
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
    public Client createClient(String name, String mail, String password) {
        Client client = new Client();
        client.setName(name);
        client.setMail(mail);
        client.setPassword(password);

        return clientRepository.createClient(client);
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
