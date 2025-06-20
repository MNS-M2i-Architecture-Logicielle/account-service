package org.example.mnb.adapters.out;

import org.example.mnb.application.ports.out.AccountRepository;
import org.example.mnb.domain.Account;
import org.example.mnb.domain.persistence.SpringAccountJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaAccountRepository implements AccountRepository {
    private final SpringAccountJpaRepository springAccountJpaRepository;

    @Autowired
    public JpaAccountRepository(SpringAccountJpaRepository springAccountJpaRepository) {
        this.springAccountJpaRepository = springAccountJpaRepository;
    }

    @Override
    public List<Account> findAll() {
        return springAccountJpaRepository.findAll();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return springAccountJpaRepository.findById(id);
    }

    @Override
    public Account save(Account account) {
        return springAccountJpaRepository.save(account);
    }

    @Override
    public void deleteById(Long id) {
        springAccountJpaRepository.deleteById(id);
    }
}
