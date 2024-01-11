package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.Card;
import by.vadzimmatsiushonak.bank.api.repository.CardRepository;
import by.vadzimmatsiushonak.bank.api.service.impl.CardServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.TestConstants.CARD_NUMBER;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @InjectMocks
    private CardServiceImpl service;

    @Mock
    private CardRepository repository;

    @Nested
    public class CardServiceTestCreate {
        @Test
        public void save() {
            Card expected = new Card();
            expected.setCardNumber(CARD_NUMBER);

            Card card = new Card();
            when(repository.save(card)).thenReturn(expected);


            Card actual = service.save(card);
            assertEquals(expected, actual);
            verify(repository).save(card);
        }

        @Test
        public void saveWithId() {
            Card expected = new Card();
            expected.setCardNumber(CARD_NUMBER);

            Card card = new Card();
            when(repository.save(card)).thenReturn(expected);

            Card cardWithId = new Card();
            cardWithId.setCardNumber(CARD_NUMBER);


            Card actual = service.save(cardWithId);
            assertEquals(expected, actual);
            verify(repository).save(card);
        }
    }

    @Nested
    public class CardServiceTestFindById {
        @Test
        public void findById() {
            Card card = new Card();
            card.setCardNumber(CARD_NUMBER);
            when(repository.findById(CARD_NUMBER)).thenReturn(Optional.of(card));


            Card actual = service.findByCardNumber(CARD_NUMBER).orElse(null);
            assertEquals(card, actual);
            verify(repository).findById(CARD_NUMBER);
        }
    }

    @Nested
    public class CardServiceTestFindAll {
        @Test
        public void findAll() {
            Card card = new Card();
            card.setCardNumber(CARD_NUMBER);
            List<Card> expected = List.of(card);
            when(repository.findAll()).thenReturn(expected);


            List<Card> actual = service.findAll();
            assertEquals(expected, actual);
            verify(repository).findAll();
        }
    }

    @Nested
    public class CardServiceTestUpdate {
        @Test
        public void update() {
            Card expected = new Card();
            expected.setCardNumber(CARD_NUMBER);
            when(repository.save(expected)).thenReturn(expected);


            service.update(expected);
            verify(repository).save(expected);
        }

        @Test
        public void updateWithEmptyIban() {
            Card expected = new Card();

            assertThrows(
                    NullPointerException.class,
                    () -> service.update(expected));
            verify(repository, times(0)).save(any());
        }
    }

    @Nested
    public class CardServiceTestDelete {
        @Test
        public void delete() {
            Card expected = new Card();

            service.delete(expected);
            verify(repository).delete(expected);
        }
    }

    @Nested
    public class CardServiceTestDeleteById {
        @Test
        public void deleteById() {
            service.deleteByCardNumber(CARD_NUMBER);
            verify(repository).deleteById(CARD_NUMBER);
        }
    }
}
