package org.example.mnb.dtos;

import lombok.Getter;
import lombok.Setter;


import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a bank account.
 *
 * This DTO shows the informqtions of a bqnk qccount
 */
@Getter @Setter
public class AccountDTO {
    private Long id;
    private double balance;
    private ClientDTO client;
}
