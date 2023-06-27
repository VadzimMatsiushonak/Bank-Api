package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.BankCard;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface BankCardService {

    BankCard save(@NotNull BankCard bankCard);

    Optional<BankCard> findById(@NotNull Long id);

    List<BankCard> findAll();

    void update(@NotNull BankCard bankCard);

    void delete(@NotNull BankCard bankCard);

    void deleteById(@NotNull Long id);
}
