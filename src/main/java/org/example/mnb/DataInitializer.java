package org.example.mnb;

import org.example.mnb.application.ports.in.AccountUseCase;
import org.example.mnb.application.ports.in.ClientUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final ClientUseCase clientUseCase;
    private final AccountUseCase accountUseCase;

    public DataInitializer(ClientUseCase clientUseCase, AccountUseCase accountUseCase) {
        this.clientUseCase = clientUseCase;
        this.accountUseCase = accountUseCase;
    }

    // This code fills the database at start-up but fails the compilation if the persistence-service is not running
    // Consider commenting the line below to avoid failures if testing without persistence-service running
    @Override
    public void run (String... args) {
        //clientUseCase.createClient("Benjamin Lecossois", "admin@mail.com", "admin");
        //accountUseCase.createAccount(1L, 10000);
    }
}
