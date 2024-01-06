package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.repository.BankPaymentRepository;
import by.vadzimmatsiushonak.bank.api.service.impl.BankPaymentServiceImpl;
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
public class BankPaymentServiceTest {

    @InjectMocks
    private BankPaymentServiceImpl service;

    @Mock
    private BankPaymentRepository repository;

    @Nested
    public class BankPaymentServiceTestCreate {
        @Test
        public void save() {
            BankPayment expected = new BankPayment();
            expected.setId(ID_LONG);

            BankPayment BankPayment = new BankPayment();
            when(repository.save(BankPayment)).thenReturn(expected);


            BankPayment actual = service.save(BankPayment);
            assertEquals(expected, actual);
            verify(repository).save(expected);
        }

        @Test
        public void saveWithId() {
            BankPayment expected = new BankPayment();
            expected.setId(ID_LONG);

            BankPayment bankPayment = new BankPayment();
            when(repository.save(bankPayment)).thenReturn(expected);

            BankPayment bankPaymentWithId = new BankPayment();
            bankPaymentWithId.setId(ID_LONG);

            BankPayment actual = service.save(bankPaymentWithId);
            assertEquals(expected, actual);
            verify(repository).save(expected);
        }
    }

    @Nested
    public class BankPaymentServiceTestFindById {
        @Test
        public void findById() {
            BankPayment bankPayment = new BankPayment();
            bankPayment.setId(ID_LONG);
            when(repository.findById(ID_LONG)).thenReturn(Optional.of(bankPayment));


            BankPayment actual = service.findById(ID_LONG).orElse(null);
            assertEquals(bankPayment, actual);
            verify(repository).findById(ID_LONG);
        }
    }

    @Nested
    public class BankPaymentServiceTestFindAll {
        @Test
        public void findAll() {
            BankPayment bankPayment = new BankPayment();
            bankPayment.setId(ID_LONG);
            List<BankPayment> expected = List.of(bankPayment);
            when(repository.findAll()).thenReturn(expected);


            List<BankPayment> actual = service.findAll();
            assertEquals(expected, actual);
            verify(repository).findAll();
        }
    }

    @Nested
    public class BankPaymentServiceTestUpdate {
        @Test
        public void update() {
            BankPayment expected = new BankPayment();
            expected.setId(ID_LONG);
            when(repository.save(expected)).thenReturn(expected);


            service.update(expected);
            verify(repository).save(expected);
        }

        @Test
        public void updateWithEmptyIban() {
            BankPayment expected = new BankPayment();

            assertThrows(
                    NullPointerException.class,
                    () -> service.update(expected));
            verify(repository, times(0)).save(any());
        }
    }

    @Nested
    public class BankPaymentServiceTestDelete {
        @Test
        public void delete() {
            BankPayment expected = new BankPayment();

            service.delete(expected);
            verify(repository).delete(expected);
        }
    }

    @Nested
    public class BankPaymentServiceTestDeleteById {
        @Test
        public void deleteById() {
            service.deleteById(ID_LONG);
            verify(repository).deleteById(ID_LONG);
        }
    }
}