package org.example.mnb.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Client {

    private Long id;
    private String name;
    private String mail;
    private String password;

    private List<Account> account = new ArrayList<>();
}
