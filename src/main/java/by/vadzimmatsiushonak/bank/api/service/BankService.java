package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.Bank;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface BankService {

    Bank save(@NotNull Bank bank);

    Optional<Bank> findById(@NotNull Long id);

    List<Bank> findAll();

    Bank update(@NotNull Bank bank);

    void delete(@NotNull Bank bank);

    void deleteById(@NotNull Long id);
}
