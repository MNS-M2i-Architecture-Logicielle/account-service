package org.example.mnb.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
    private Long id;
    private double balance;
    private ClientDTO client;
}
