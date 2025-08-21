package org.example.mnb.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Client {

    private Long id;
    private String name;
    private String mail;
    private List<Account> account;
}
