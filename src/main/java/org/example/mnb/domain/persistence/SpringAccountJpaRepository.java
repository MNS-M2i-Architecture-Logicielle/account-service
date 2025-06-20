package org.example.mnb.domain.persistence;

import org.example.mnb.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringAccountJpaRepository extends JpaRepository<Account, Long> {
}
