package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.BankCard;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface BankCardService {
    void create(@NotNull BankCard bankCard);

    Optional<BankCard> findById(@NotNull Long id);

    List<BankCard> findAll();

    void update(@NotNull BankCard bankCard);

    void delete(@NotNull BankCard bankCard);

    void deleteById(@NotNull Long id);
}
