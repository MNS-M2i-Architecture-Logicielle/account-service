package org.example.mnb.controllers;

import org.example.mnb.dtos.ClientDTO;
import org.example.mnb.entities.Client;
import org.example.mnb.services.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing clients.
 *
 * Exposes endpoints under the base path "/api/account" to:
 * - retrieve a list of client,
 * - retrieve the details of a specific client
 * - create a new  client,
 *
 * Each method delegates logic to the ClientService.
 */

@RestController
@RequestMapping("api/client")
public class ClientController {
    
    private final ClientService clientService;

    public ClientController(ClientService clientService) {this.clientService = clientService;}
    
    @GetMapping
    public List<Client> getAllClients(){
        return clientService.getAllClients();
    }
    
    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientService.getClient(id);
    }
    
    @PostMapping("/new")
    public String createClient(@RequestBody ClientDTO client){
        clientService.createClient(client);
        return "Client successfully created !";
    }
}
