package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.Transaction;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface TransactionService {

    Transaction save(@NotNull Transaction transaction);

    Optional<Transaction> findById(@NotNull Long id);

    List<Transaction> findAll();

    Transaction update(@NotNull Transaction transaction);

    void delete(@NotNull Transaction transaction);

    void deleteById(@NotNull Long id);
}
