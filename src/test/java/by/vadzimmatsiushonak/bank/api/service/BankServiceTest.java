package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import by.vadzimmatsiushonak.bank.api.repository.BankRepository;
import by.vadzimmatsiushonak.bank.api.service.impl.BankServiceImpl;
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
import static utils.TestConstants.ID_LONG;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @InjectMocks
    private BankServiceImpl service;

    @Mock
    private BankRepository repository;

    @Nested
    public class BankServiceTestCreate {
        @Test
        public void save() {
            Bank expected = new Bank();
            expected.setId(ID_LONG);

            Bank bank = new Bank();
            when(repository.save(bank)).thenReturn(expected);


            Bank actual = service.save(bank);
            assertEquals(expected, actual);
            verify(repository).save(expected);
        }

        @Test
        public void saveWithId() {
            Bank expected = new Bank();
            expected.setId(ID_LONG);

            Bank bank = new Bank();
            when(repository.save(bank)).thenReturn(expected);

            Bank bankWithId = new Bank();
            bankWithId.setId(ID_LONG);

            Bank actual = service.save(bankWithId);
            assertEquals(expected, actual);
            verify(repository).save(expected);
        }
    }

    @Nested
    public class BankServiceTestFindById {
        @Test
        public void findById() {
            Bank bank = new Bank();
            bank.setId(ID_LONG);
            when(repository.findById(ID_LONG)).thenReturn(Optional.of(bank));


            Bank actual = service.findById(ID_LONG).orElse(null);
            assertEquals(bank, actual);
            verify(repository).findById(ID_LONG);
        }
    }

    @Nested
    public class BankServiceTestFindAll {
        @Test
        public void findAll() {
            Bank bank = new Bank();
            bank.setId(ID_LONG);
            List<Bank> expected = List.of(bank);
            when(repository.findAll()).thenReturn(expected);


            List<Bank> actual = service.findAll();
            assertEquals(expected, actual);
            verify(repository).findAll();
        }
    }

    @Nested
    public class BankServiceTestUpdate {
        @Test
        public void update() {
            Bank expected = new Bank();
            expected.setId(ID_LONG);
            when(repository.save(expected)).thenReturn(expected);


            service.update(expected);
            verify(repository).save(expected);
        }

        @Test
        public void updateWithEmptyIban() {
            Bank expected = new Bank();

            assertThrows(
                    NullPointerException.class,
                    () -> service.update(expected));
            verify(repository, times(0)).save(any());
        }
    }

    @Nested
    public class BankServiceTestDelete {
        @Test
        public void delete() {
            Bank expected = new Bank();

            service.delete(expected);
            verify(repository).delete(expected);
        }
    }

    @Nested
    public class BankServiceTestDeleteById {
        @Test
        public void deleteById() {
            service.deleteById(ID_LONG);
            verify(repository).deleteById(ID_LONG);
        }
    }
}