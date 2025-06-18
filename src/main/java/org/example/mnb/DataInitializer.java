package org.example.mnb;

import org.example.mnb.entities.Account;
import org.example.mnb.entities.Client;
import org.example.mnb.repositories.AccountRepository;
import org.example.mnb.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    
    public DataInitializer(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }
    
    @Override
    public void run (String... args) {
        Client client;
        if (clientRepository.count() == 0) {
            client = new Client();
            client.setName("Benjamin Lecossois");
            client.setMail("admin@mail.com");
            client = clientRepository.save(client);
        } else {
            client = clientRepository.findById(1L).orElseThrow();
        }

        if (accountRepository.count() == 0) {
            Account account = new Account();
            account.setClient(client);
            account.setBalance(150000);
            accountRepository.save(account);
        }

    }
}
