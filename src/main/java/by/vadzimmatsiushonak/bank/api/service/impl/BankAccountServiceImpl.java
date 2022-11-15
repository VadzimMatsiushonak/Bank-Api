package by.vadzimmatsiushonak.bank.api.service.impl;

import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.repository.BankAccountRepository;
import by.vadzimmatsiushonak.bank.api.service.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository repository;

    @Override
    public void create(@NotNull BankAccount bankAccount) {
        log.info("BankAccountServiceImpl create {}", bankAccount);

        repository.save(bankAccount);
    }

    @Override
    public Optional<BankAccount> findById(@NotNull Long id) {
        log.info("BankAccountServiceImpl findById {}", id);

        return repository.findById(id);
    }

    @Override
    public List<BankAccount> findAll() {
        log.info("BankAccountServiceImpl findAll");

        return repository.findAll();
    }

    @Override
    public void update(@NotNull BankAccount bankAccount) {
        log.info("BankAccountServiceImpl update {}", bankAccount);

        Objects.requireNonNull(bankAccount.getId());
        repository.save(bankAccount);
    }

    @Override
    public void delete(@NotNull BankAccount bankAccount) {
        log.info("BankAccountServiceImpl delete {}", bankAccount);

        repository.delete(bankAccount);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        log.info("BankAccountServiceImpl deleteById {}", id);

        repository.deleteById(id);
    }

}
