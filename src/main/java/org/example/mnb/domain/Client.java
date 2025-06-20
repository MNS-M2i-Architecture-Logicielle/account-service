package org.example.mnb.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * JPA entity representing a client of the bank.
 *
 * A client can own one or more bank accounts. This entity is mapped to the "client" table
 * in the database and includes basic personal information such as name and email.
 */
@Entity
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String mail;
    
    @OneToMany(mappedBy = "client")
    @JsonManagedReference
    private List<Account> account;
}
