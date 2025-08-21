package org.example.mnb.adapters.out.account;

import org.example.mnb.domain.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@FeignClient(name = "account-persistence-service", url = "${services.persistence.url}")
public interface AccountPersistenceClient {

    @GetMapping("/accounts")
    List<Account> findAll();

    @GetMapping("/accounts/{id}")
    Optional<Account> findById(@PathVariable("id") Long id);

    @PostMapping("/accounts")
    Account save(@RequestBody Account account);

    @DeleteMapping("/accounts/{id}")
    void deleteById(@PathVariable("id") Long id);
}

