package org.example.mnb.adapters.in;

import org.example.mnb.adapters.in.dto.request.ClientCreationRequest;
import org.example.mnb.adapters.in.dto.response.ClientResponse;
import org.example.mnb.application.ports.in.ClientUseCase;
import org.example.mnb.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/client")
public class ClientController {
    
    private final ClientUseCase clientUseCase;

    @Autowired
    public ClientController(ClientUseCase clientUseCase) {
        this.clientUseCase = clientUseCase;
    }
    
    @GetMapping
    public List<ClientResponse> getAllClients() {
        return clientUseCase.getAllClients().stream()
                .map(this::mapToClientResponse)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ClientResponse getClientById(@PathVariable Long id) {
        Client client = clientUseCase.getClientById(id);
        return mapToClientResponse(client);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse createClient(@RequestBody ClientCreationRequest request) {
        Client createdClient = clientUseCase.createClient(request.getName(), request.getMail());
        return mapToClientResponse(createdClient);
    }
    
    @PutMapping("/{id}")
    public ClientResponse updateClient(@PathVariable Long id, @RequestBody Client client) {
        Client updatedClient = clientUseCase.updateClient(id, client);
        return mapToClientResponse(updatedClient);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        clientUseCase.deleteClient(id);
    }
    
    private ClientResponse mapToClientResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getMail(),
                client.getAccount()
        );
    }
}
