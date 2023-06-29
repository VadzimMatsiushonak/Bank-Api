package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.exception.DuplicateException;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.repository.BankAccountRepository;
import by.vadzimmatsiushonak.bank.api.service.impl.BankAccountServiceImpl;
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
import static utils.TestConstants.IBAN;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {

    @InjectMocks
    private BankAccountServiceImpl service;

    @Mock
    private BankAccountRepository repository;

    @Nested
    public class BankAccountServiceTestCreate {
        @Test
        public void save() {
            BankAccount expected = new BankAccount();
            expected.setIban(IBAN);
            when(repository.findById(IBAN)).thenReturn(Optional.empty());
            when(repository.save(expected)).thenReturn(expected);


            BankAccount actual = service.save(expected);
            assertEquals(expected, actual);
            verify(repository).findById(IBAN);
            verify(repository).save(expected);
        }

        @Test
        public void saveDuplicate() {
            BankAccount expected = new BankAccount();
            expected.setIban(IBAN);
            when(repository.findById(any())).thenReturn(Optional.of(expected));


            assertThrows(
                    DuplicateException.class,
                    () -> service.save(expected));
            verify(repository).findById(IBAN);
            verify(repository, times(0)).save(any());
        }
    }

    @Nested
    public class BankAccountServiceTestFindById {
        @Test
        public void findById() {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setIban(IBAN);
            when(repository.findById(IBAN)).thenReturn(Optional.of(bankAccount));


            BankAccount actual = service.findById(IBAN).orElse(null);
            assertEquals(bankAccount, actual);
            verify(repository).findById(IBAN);
        }
    }

    @Nested
    public class BankAccountServiceTestFindAll {
        @Test
        public void findAll() {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setIban(IBAN);
            List<BankAccount> expected = List.of(bankAccount);
            when(repository.findAll()).thenReturn(expected);


            List<BankAccount> actual = service.findAll();
            assertEquals(expected, actual);
            verify(repository).findAll();
        }
    }

    @Nested
    public class BankAccountServiceTestUpdate {
        @Test
        public void update() {
            BankAccount expected = new BankAccount();
            expected.setIban(IBAN);
            when(repository.save(expected)).thenReturn(expected);


            service.update(expected);
            verify(repository).save(expected);
        }

        @Test
        public void updateWithEmptyIban() {
            BankAccount expected = new BankAccount();

            assertThrows(
                    NullPointerException.class,
                    () -> service.update(expected));
            verify(repository, times(0)).save(any());
        }
    }

    @Nested
    public class BankAccountServiceTestDelete {
        @Test
        public void delete() {
            BankAccount expected = new BankAccount();

            service.delete(expected);
            verify(repository).delete(expected);
        }
    }

    @Nested
    public class BankAccountServiceTestDeleteById {
        @Test
        public void deleteById() {
            service.deleteById(IBAN);
            verify(repository).deleteById(IBAN);
        }
    }
}
