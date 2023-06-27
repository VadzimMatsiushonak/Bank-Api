package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.BankCard;
import by.vadzimmatsiushonak.bank.api.repository.BankCardRepository;
import by.vadzimmatsiushonak.bank.api.service.impl.BankCardServiceImpl;
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
import static org.mockito.Mockito.*;
import static utils.TestConstants.TEST_ID_LONG;

@ExtendWith(MockitoExtension.class)
public class BankCardServiceTest {

    @InjectMocks
    private BankCardServiceImpl service;

    @Mock
    private BankCardRepository repository;

    @Nested
    public class BankCardServiceTestCreate {
        @Test
        public void save() {
            BankCard expected = new BankCard();
            expected.setId(TEST_ID_LONG);

            BankCard bankCard = new BankCard();
            when(repository.save(bankCard)).thenReturn(expected);


            BankCard actual = service.save(bankCard);
            assertEquals(expected, actual);
            verify(repository).save(expected);
        }

        @Test
        public void saveWithId() {
            BankCard expected = new BankCard();
            expected.setId(TEST_ID_LONG);

            BankCard bankCard = new BankCard();
            when(repository.save(bankCard)).thenReturn(expected);

            BankCard bankCardWithId = new BankCard();
            bankCardWithId.setId(TEST_ID_LONG);

            BankCard actual = service.save(bankCardWithId);
            assertEquals(expected, actual);
            verify(repository).save(expected);
        }
    }

    @Nested
    public class BankCardServiceTestFindById {
        @Test
        public void findById() {
            BankCard bankCard = new BankCard();
            bankCard.setId(TEST_ID_LONG);
            when(repository.findById(TEST_ID_LONG)).thenReturn(Optional.of(bankCard));


            BankCard actual = service.findById(TEST_ID_LONG).orElse(null);
            assertEquals(bankCard, actual);
            verify(repository).findById(TEST_ID_LONG);
        }
    }

    @Nested
    public class BankCardServiceTestFindAll {
        @Test
        public void findAll() {
            BankCard bankCard = new BankCard();
            bankCard.setId(TEST_ID_LONG);
            List<BankCard> expected = List.of(bankCard);
            when(repository.findAll()).thenReturn(expected);


            List<BankCard> actual = service.findAll();
            assertEquals(expected, actual);
            verify(repository).findAll();
        }
    }

    @Nested
    public class BankCardServiceTestUpdate {
        @Test
        public void update() {
            BankCard expected = new BankCard();
            expected.setId(TEST_ID_LONG);
            when(repository.save(expected)).thenReturn(expected);


            service.update(expected);
            verify(repository).save(expected);
        }

        @Test
        public void updateWithEmptyIban() {
            BankCard expected = new BankCard();

            assertThrows(
                    NullPointerException.class,
                    () -> service.update(expected));
            verify(repository, times(0)).save(any());
        }
    }

    @Nested
    public class BankCardServiceTestDelete {
        @Test
        public void delete() {
            BankCard expected = new BankCard();

            service.delete(expected);
            verify(repository).delete(expected);
        }
    }

    @Nested
    public class BankCardServiceTestDeleteById {
        @Test
        public void deleteById() {
            service.deleteById(TEST_ID_LONG);
            verify(repository).deleteById(TEST_ID_LONG);
        }
    }
}