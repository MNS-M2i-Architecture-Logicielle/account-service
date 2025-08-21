package org.example.mnb.adapters.out.account;

import org.example.mnb.domain.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Repository
@FeignClient(name = "persistence-service", url = "${persistence.service.url}")
public interface AccountPersistenceClient {

    @GetMapping("/persistence/accounts")
    List<Account> findAll();

    @GetMapping("/persistence/accounts/{id}")
    Optional<Account> findById(@PathVariable("id") Long id);

    @PostMapping("/persistence/accounts")
    Account save(@RequestBody Account account);

    @DeleteMapping("/persistence/accounts/{id}")
    void deleteById(@PathVariable("id") Long id);
}

