package org.example.mnb.adapters.out.client;

import org.example.mnb.domain.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "persistence-service", url = "${persistence.service.url}/api/client")
public interface ClientPersistenceClient {

    @GetMapping
    List<Client> getAllClients();

    @GetMapping("/{id}")
    Optional<Client> getClientById(@PathVariable("id") Long id);

    @PostMapping
    Client createClient(@RequestBody Client client);

    @PutMapping("/{id}")
    Client updateClient(@PathVariable("id") Long id, @RequestBody Client client);

    @DeleteMapping("/{id}")
    void deleteClient(@PathVariable("id") Long id);
}

