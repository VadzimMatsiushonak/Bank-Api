package by.vadzimmatsiushonak.bank.api.service.impl;

import by.vadzimmatsiushonak.bank.api.model.entity.BankCard;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.repository.BankCardRepository;
import by.vadzimmatsiushonak.bank.api.service.BankCardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class BankCardServiceImpl implements BankCardService {

    private final BankCardRepository repository;

    @Override
    public void create(@NotNull BankCard bankCard) {
        log.info("BankCardServiceImpl create {}", bankCard);

        repository.save(bankCard);
    }

    @Override
    public Optional<BankCard> findById(@NotNull Long id) {
        log.info("BankCardServiceImpl findById {}", id);

        return repository.findById(id);
    }

    @Override
    public List<BankCard> findAll() {
        log.info("BankCardServiceImpl findAll");

        List<BankCard> all = repository.findAll();
        for (BankCard bankCard : all) {
            for (BankPayment bankPayment : bankCard.getBankAccount().getBankPayments()) {
                bankPayment.hashCode();
            }
        }
        return all;
    }

    @Override
    public void update(@NotNull BankCard bankCard) {
        log.info("BankCardServiceImpl update {}", bankCard);

        Objects.requireNonNull(bankCard.getId());
        repository.save(bankCard);
    }

    @Override
    public void delete(@NotNull BankCard bankCard) {
        log.info("BankCardServiceImpl delete {}", bankCard);

        repository.delete(bankCard);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        log.info("BankCardServiceImpl deleteById {}", id);

        repository.deleteById(id);
    }
}
