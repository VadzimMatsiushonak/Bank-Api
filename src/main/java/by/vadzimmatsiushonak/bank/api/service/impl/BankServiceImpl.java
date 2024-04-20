package by.vadzimmatsiushonak.bank.api.service.impl;

import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import by.vadzimmatsiushonak.bank.api.repository.BankRepository;
import by.vadzimmatsiushonak.bank.api.service.BankService;
import by.vadzimmatsiushonak.bank.api.service.ReferenceService;
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
public class BankServiceImpl implements BankService {

    private final BankRepository repository;
    private final ReferenceService referenceService;

    @Override
    public Bank save(@NotNull Bank bank) {
        log.info("BankServiceImpl create {}", bank);
        bank.setId(null);
        referenceService.setReferences(bank);

        return repository.save(bank);
    }

    @Override
    public Optional<Bank> findById(@NotNull Long id) {
        log.info("BankServiceImpl findById {}", id);

        return repository.findById(id);
    }

    @Override
    public List<Bank> findAll() {
        log.info("BankServiceImpl findAll");

        return repository.findAll();
    }

    @Override
    public Bank update(@NotNull Bank bank) {
        log.info("BankServiceImpl update {}", bank);

        Objects.requireNonNull(bank.getId());
        return repository.save(bank);
    }

    @Override
    public void delete(@NotNull Bank bank) {
        log.info("BankServiceImpl delete {}", bank);

        repository.delete(bank);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        log.info("BankServiceImpl deleteById {}", id);

        repository.deleteById(id);
    }
}
