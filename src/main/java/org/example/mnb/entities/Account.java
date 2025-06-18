package org.example.mnb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private double balance = 0;
    
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
