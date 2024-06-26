package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.Account;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface AccountService {

    Account save(@NotNull Account account);

    Optional<Account> findByIban(@NotNull String iban);

    List<Account> findAll();

    Account update(@NotNull Account account);

    void delete(@NotNull Account account);

    void deleteByIban(@NotNull String iban);
}
