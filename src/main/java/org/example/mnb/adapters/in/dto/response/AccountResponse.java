package org.example.mnb.adapters.in.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountResponse {
    private Long id;
    private double balance;
    private Long clientId;
}
