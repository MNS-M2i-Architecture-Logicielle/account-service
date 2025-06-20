package org.example.mnb.adapters.in.dto.request;

import lombok.Getter;

@Getter
public class AccountCreationRequest {
    private Long clientId;
    private double initialBalance;
}
