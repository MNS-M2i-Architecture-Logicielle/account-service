package org.example.mnb;

import org.example.mnb.application.ports.in.ClientUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final ClientUseCase clientUseCase;

    public DataInitializer(ClientUseCase clientUseCase) {
        this.clientUseCase = clientUseCase;
    }

    @Override
    public void run (String... args) {
        clientUseCase.createClient("Benjamin Lecossois", "admin@mail.com");
    }
}
