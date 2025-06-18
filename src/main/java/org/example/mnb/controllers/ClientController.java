package org.example.mnb.controllers;

import org.example.mnb.dtos.ClientDTO;
import org.example.mnb.entities.Client;
import org.example.mnb.services.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Client getCient(@PathVariable Long id) {
        return clientService.getClient(id);
    }
    
    @PostMapping("/new")
    public String createClient(@RequestBody ClientDTO client){
        clientService.createClient(client);
        return "Client successfully created !";
    }
}
