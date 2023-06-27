package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface BankAccountService {

    BankAccount save(@NotNull BankAccount bankAccount);

    Optional<BankAccount> findById(@NotBlank String iban);

    List<BankAccount> findAll();

    void update(@NotNull BankAccount bankAccount);

    void delete(@NotNull BankAccount bankAccount);

    void deleteById(@NotBlank String iban);

}
