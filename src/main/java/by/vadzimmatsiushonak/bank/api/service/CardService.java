package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.Card;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CardService {

    Card save(@NotNull Card card);

    Optional<Card> findByCardNumber(@NotNull String cardNumber);

    List<Card> findAll();

    Card update(@NotNull Card card);

    void delete(@NotNull Card card);

    void deleteByCardNumber(@NotNull String cardNumber);
}
