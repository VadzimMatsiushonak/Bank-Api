package by.vadzimmatsiushonak.bank.api.service.impl;

import by.vadzimmatsiushonak.bank.api.model.entity.Card;
import by.vadzimmatsiushonak.bank.api.repository.CardRepository;
import by.vadzimmatsiushonak.bank.api.service.CardService;
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
public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final ReferenceService referenceService;

    @Override
    public Card save(@NotNull Card card) {
        log.info("CardServiceImpl create {}", card);
        referenceService.setReferences(card);

        return repository.save(card);
    }

    @Override
    public Optional<Card> findByCardNumber(@NotNull String cardNumber) {
        log.info("CardServiceImpl findByCardNumber {}", cardNumber);

        return repository.findById(cardNumber);
    }

    @Override
    public List<Card> findAll() {
        log.info("CardServiceImpl findAll");

        return repository.findAll();
    }

    @Override
    public Card update(@NotNull Card card) {
        log.info("CardServiceImpl update {}", card);

        Objects.requireNonNull(card.getCardNumber());
        return repository.save(card);
    }

    @Override
    public void delete(@NotNull Card card) {
        log.info("CardServiceImpl delete {}", card);

        repository.delete(card);
    }

    @Override
    public void deleteByCardNumber(@NotNull String cardNumber) {
        log.info("CardServiceImpl deleteById {}", cardNumber);

        repository.deleteById(cardNumber);
    }
}
