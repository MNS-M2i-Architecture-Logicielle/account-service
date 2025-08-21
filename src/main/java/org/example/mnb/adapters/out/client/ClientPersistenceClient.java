package org.example.mnb.adapters.out.client;

import org.example.mnb.domain.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "client-persistence-service", url = "${services.persistence.url}")
public interface ClientPersistenceClient {

    @GetMapping("/clients")
    List<Client> getAllClients();

    @GetMapping("/clients/{id}")
    Optional<Client> getClientById(@PathVariable("id") Long id);

    @PostMapping("/clients")
    Client createClient(@RequestBody Client client);

    @PutMapping("/clients/{id}")
    Client updateClient(@PathVariable("id") Long id, @RequestBody Client client);

    @DeleteMapping("/clients/{id}")
    void deleteClient(@PathVariable("id") Long id);
}

