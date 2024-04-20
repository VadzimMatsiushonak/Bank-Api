package by.vadzimmatsiushonak.bank.api.service.impl;

import by.vadzimmatsiushonak.bank.api.model.entity.Account;
import by.vadzimmatsiushonak.bank.api.repository.AccountRepository;
import by.vadzimmatsiushonak.bank.api.service.AccountService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Validated
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Override
    public Account save(@NotNull Account account) {
        log.info("AccountServiceImpl create {}", account);
        account.setIban(null);

        return repository.save(account);
    }

    @Override
    public Optional<Account> findByIban(@NotNull String iban) {
        log.info("AccountServiceImpl findByIban {}", iban);

        return repository.findById(iban);
    }

    @Override
    public List<Account> findAll() {
        log.info("AccountServiceImpl findAll");

        return repository.findAll();
    }

    @Override
    public Account update(@NotNull Account account) {
        log.info("AccountServiceImpl update {}", account);

        Objects.requireNonNull(account.getIban());
        return repository.save(account);
    }

    @Override
    public void delete(@NotNull Account account) {
        log.info("AccountServiceImpl delete {}", account);

        repository.delete(account);
    }

    @Override
    public void deleteByIban(@NotNull String iban) {
        log.info("AccountServiceImpl deleteByIban {}", iban);

        repository.deleteById(iban);
    }
}
