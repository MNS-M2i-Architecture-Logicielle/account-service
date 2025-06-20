package org.example.mnb.adapters.out;

import org.example.mnb.application.ports.out.ClientRepository;
import org.example.mnb.domain.Client;
import org.example.mnb.domain.persistence.SpringClientJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaClientRepository implements ClientRepository {
    private final SpringClientJpaRepository springClientJpaRepository;

    @Autowired
    public JpaClientRepository(SpringClientJpaRepository springClientJpaRepository) {
        this.springClientJpaRepository = springClientJpaRepository;
    }

    @Override
    public List<Client> findAll() {
        return springClientJpaRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return springClientJpaRepository.findById(id);
    }

    @Override
    public Client save(Client client) {
        return springClientJpaRepository.save(client);
    }

    @Override
    public void deleteById(Long id) {
        springClientJpaRepository.deleteById(id);
    }
}
