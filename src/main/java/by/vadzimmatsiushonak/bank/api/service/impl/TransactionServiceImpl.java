package by.vadzimmatsiushonak.bank.api.service.impl;

import by.vadzimmatsiushonak.bank.api.model.entity.Transaction;
import by.vadzimmatsiushonak.bank.api.repository.TransactionRepository;
import by.vadzimmatsiushonak.bank.api.service.ReferenceService;
import by.vadzimmatsiushonak.bank.api.service.TransactionService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Validated
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final ReferenceService referenceService;

    @Override
    public Transaction save(@NotNull Transaction transaction) {
        log.info("TransactionServiceImpl create {}", transaction);
        transaction.setId(null);
        referenceService.setReferences(transaction);

        return repository.save(transaction);
    }

    @Override
    public Optional<Transaction> findById(@NotNull Long id) {
        log.info("TransactionServiceImpl findById {}", id);

        return repository.findById(id);
    }

    @Override
    public List<Transaction> findAll() {
        log.info("TransactionServiceImpl findAll");

        return repository.findAll();
    }

    @Override
    public Transaction update(@NotNull Transaction transaction) {
        log.info("TransactionServiceImpl update {}", transaction);

        Objects.requireNonNull(transaction.getId());
        return repository.save(transaction);
    }

    @Override
    public void delete(@NotNull Transaction transaction) {
        log.info("TransactionServiceImpl delete {}", transaction);

        repository.delete(transaction);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        log.info("TransactionServiceImpl deleteById {}", id);

        repository.deleteById(id);
    }
}
