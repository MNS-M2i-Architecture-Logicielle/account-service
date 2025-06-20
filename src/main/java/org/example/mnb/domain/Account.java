package org.example.mnb.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing a bank account in the system.
 *
 * Each account is associated with a single client and holds a balance.
 * This entity is mapped to the "account" table in the database.
 *
 * An account cannot exist without being linked to a client.
 */

@Entity
@Getter @Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance = 0;
    
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference
    private Client client;
}
