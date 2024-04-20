package by.vadzimmatsiushonak.bank.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.TestConstants.IBAN;

import by.vadzimmatsiushonak.bank.api.model.entity.Account;
import by.vadzimmatsiushonak.bank.api.repository.AccountRepository;
import by.vadzimmatsiushonak.bank.api.service.impl.AccountServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl service;

    @Mock
    private AccountRepository repository;

    @Nested
    public class AccountServiceTestCreate {

        @Test
        public void save() {
            Account expected = new Account();
            expected.setIban(IBAN);

            Account account = new Account();
            when(repository.save(account)).thenReturn(expected);

            Account actual = service.save(account);
            assertEquals(expected, actual);
            verify(repository).save(account);
        }

        @Test
        public void saveWithId() {
            Account expected = new Account();
            expected.setIban(IBAN);

            Account account = new Account();
            when(repository.save(account)).thenReturn(expected);

            Account accountWithId = new Account();
            accountWithId.setIban(IBAN);

            Account actual = service.save(accountWithId);
            assertEquals(expected, actual);
            verify(repository).save(account);
        }
    }

    @Nested
    public class AccountServiceTestFindById {

        @Test
        public void findById() {
            Account account = new Account();
            account.setIban(IBAN);
            when(repository.findById(IBAN)).thenReturn(Optional.of(account));

            Account actual = service.findByIban(IBAN).orElse(null);
            assertEquals(account, actual);
            verify(repository).findById(IBAN);
        }
    }

    @Nested
    public class AccountServiceTestFindAll {

        @Test
        public void findAll() {
            Account account = new Account();
            account.setIban(IBAN);
            List<Account> expected = List.of(account);
            when(repository.findAll()).thenReturn(expected);

            List<Account> actual = service.findAll();
            assertEquals(expected, actual);
            verify(repository).findAll();
        }
    }

    @Nested
    public class AccountServiceTestUpdate {

        @Test
        public void update() {
            Account expected = new Account();
            expected.setIban(IBAN);
            when(repository.save(expected)).thenReturn(expected);

            service.update(expected);
            verify(repository).save(expected);
        }

        @Test
        public void updateWithEmptyIban() {
            Account expected = new Account();

            assertThrows(
                NullPointerException.class,
                () -> service.update(expected));
            verify(repository, times(0)).save(any());
        }
    }

    @Nested
    public class AccountServiceTestDelete {

        @Test
        public void delete() {
            Account expected = new Account();

            service.delete(expected);
            verify(repository).delete(expected);
        }
    }

    @Nested
    public class AccountServiceTestDeleteById {

        @Test
        public void deleteById() {
            service.deleteByIban(IBAN);
            verify(repository).deleteById(IBAN);
        }
    }
}
