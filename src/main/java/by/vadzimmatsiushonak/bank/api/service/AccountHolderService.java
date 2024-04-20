package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.AccountHolder;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface AccountHolderService {

    AccountHolder save(@NotNull AccountHolder accountHolder);

    Optional<AccountHolder> findById(@NotNull Long id);

    Optional<AccountHolder> findByUserId(@NotNull Long id);

    Optional<AccountHolder> findByUserLogin(@NotNull String login);

    List<AccountHolder> findAll();

    AccountHolder update(@NotNull AccountHolder accountHolder);

    void delete(@NotNull AccountHolder accountHolder);

    void deleteById(@NotNull Long id);
}
