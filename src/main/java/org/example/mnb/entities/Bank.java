package org.example.mnb.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * JPA entity representing a bank.
 *
 * This entity corresponds to a banking institution within the system.
 * It is mapped to the "bank" table in the database.
 */
@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
