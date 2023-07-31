package by.vadzimmatsiushonak.bank.api.service.impl;

import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.repository.BankAccountRepository;
import by.vadzimmatsiushonak.bank.api.service.BankAccountService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_DuplicateException;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository repository;

    @Override
    public BankAccount save(@NotNull BankAccount bankAccount) {
        log.info("BankAccountServiceImpl create {}", bankAccount);

        repository.findById(bankAccount.getIban()).ifPresent(existing -> {
            throw new_DuplicateException(existing.getIban());
        });

        return repository.save(bankAccount);
    }

    @Override
    public Optional<BankAccount> findById(@NotBlank String iban) {
        log.info("BankAccountServiceImpl findById {}", iban);

        return repository.findById(iban);
    }

    @Override
    public List<BankAccount> findAll() {
        log.info("BankAccountServiceImpl findAll");

        return repository.findAll();
    }

    @Override
    public void update(@NotNull BankAccount bankAccount) {

        Objects.requireNonNull(bankAccount.getIban());
        repository.save(bankAccount);
    }

    @Override
    public void delete(@NotNull BankAccount bankAccount) {
        log.info("BankAccountServiceImpl delete {}", bankAccount);

        repository.delete(bankAccount);
    }

    @Override
    public void deleteById(@NotBlank String iban) {
        log.info("BankAccountServiceImpl deleteById {}", iban);

        repository.deleteById(iban);
    }

}
