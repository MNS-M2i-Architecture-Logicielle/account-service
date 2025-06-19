package org.example.mnb.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * Data Transfer Object (DTO) representing a client.
 *
 * This DTO exposes the informqtions of a customer,
 */
@Getter @Setter
public class ClientDTO {
    private Long id;
    private String name;
    private String mail;
    private List<AccountDTO> accounts;
}
