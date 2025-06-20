package org.example.mnb.domain.persistence;

import org.example.mnb.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringClientJpaRepository extends JpaRepository<Client, Long> {
}
