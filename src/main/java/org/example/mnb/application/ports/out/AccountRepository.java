package org.example.mnb.application.ports.out;

import org.example.mnb.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository {
    List<Account> findAll();
    Optional<Account> findById(Long id);
    Account save(Account account);
    void deleteById(Long id);
}
