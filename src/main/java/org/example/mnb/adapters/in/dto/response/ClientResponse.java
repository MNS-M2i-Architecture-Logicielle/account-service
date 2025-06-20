package org.example.mnb.adapters.in.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.mnb.domain.Account;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClientResponse {
    private Long id;
    private String name;
    private String mail;
    private List<Account> accounts;
}
