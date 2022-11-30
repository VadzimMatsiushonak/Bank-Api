package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    BankAccount create(@NotNull BankAccount bankAccount);

    Optional<BankAccount> findById(@NotNull Long id);

    List<BankAccount> findAll();

    void update(@NotNull BankAccount bankAccount);

    void delete(@NotNull BankAccount bankAccount);

    void deleteById(@NotNull Long id);

}
