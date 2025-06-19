package org.example.mnb.services;

import org.example.mnb.dtos.ClientDTO;
import org.example.mnb.entities.Account;
import org.example.mnb.entities.Client;
import org.example.mnb.exceptions.ClientNotFoundException;
import org.example.mnb.repositories.AccountRepository;
import org.example.mnb.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    public ClientService(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }
    
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }
    
    public Client getClient(Long id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }
    
    public void createClient(ClientDTO dto) {
        if (dto == null || dto.getName() == null || dto.getMail() == null) {
            throw new IllegalArgumentException("ClientDTO is invalid");
        }

        Client client = new Client();
        client.setName(dto.getName());
        client.setMail(dto.getMail());
        clientRepository.save(client);

        // Création d’un nouveau compte lié au nouveau client 
        Account account = new Account();
        // balance initialisée à zéro dans Account.class
        account.setClient(client);
        accountRepository.save(account);
    }
}
