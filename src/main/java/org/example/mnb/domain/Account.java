package org.example.mnb.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
