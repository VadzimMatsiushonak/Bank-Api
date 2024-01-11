package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.AccountHolder;
import by.vadzimmatsiushonak.bank.api.repository.AccountHolderRepository;
import by.vadzimmatsiushonak.bank.api.service.impl.AccountHolderServiceImpl;
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
import static utils.TestConstants.ID_LONG;

@ExtendWith(MockitoExtension.class)
public class AccountHolderServiceTest {

    @InjectMocks
    private AccountHolderServiceImpl service;

    @Mock
    private AccountHolderRepository repository;

    @Nested
    public class AccountHolderServiceTestCreate {
        @Test
        public void save() {
            AccountHolder expected = new AccountHolder();
            expected.setId(ID_LONG);

            AccountHolder accountHolder = new AccountHolder();
            when(repository.save(accountHolder)).thenReturn(expected);


            AccountHolder actual = service.save(accountHolder);
            assertEquals(expected, actual);
            verify(repository).save(accountHolder);
        }

        @Test
        public void saveWithId() {
            AccountHolder expected = new AccountHolder();
            expected.setId(ID_LONG);

            AccountHolder accountHolder = new AccountHolder();
            when(repository.save(accountHolder)).thenReturn(expected);

            AccountHolder accountHolderWithId = new AccountHolder();
            accountHolderWithId.setId(ID_LONG);


            AccountHolder actual = service.save(accountHolderWithId);
            assertEquals(expected, actual);
            verify(repository).save(accountHolder);
        }
    }

    @Nested
    public class AccountHolderServiceTestFindById {
        @Test
        public void findById() {
            AccountHolder accountHolder = new AccountHolder();
            accountHolder.setId(ID_LONG);
            when(repository.findById(ID_LONG)).thenReturn(Optional.of(accountHolder));


            AccountHolder actual = service.findById(ID_LONG).orElse(null);
            assertEquals(accountHolder, actual);
            verify(repository).findById(ID_LONG);
        }
    }

    @Nested
    public class AccountHolderServiceTestFindAll {
        @Test
        public void findAll() {
            AccountHolder accountHolder = new AccountHolder();
            accountHolder.setId(ID_LONG);
            List<AccountHolder> expected = List.of(accountHolder);
            when(repository.findAll()).thenReturn(expected);


            List<AccountHolder> actual = service.findAll();
            assertEquals(expected, actual);
            verify(repository).findAll();
        }
    }

    @Nested
    public class AccountHolderServiceTestUpdate {
        @Test
        public void update() {
            AccountHolder expected = new AccountHolder();
            expected.setId(ID_LONG);
            when(repository.save(expected)).thenReturn(expected);


            service.update(expected);
            verify(repository).save(expected);
        }

        @Test
        public void updateWithEmptyIban() {
            AccountHolder expected = new AccountHolder();

            assertThrows(
                    NullPointerException.class,
                    () -> service.update(expected));
            verify(repository, times(0)).save(any());
        }
    }

    @Nested
    public class AccountHolderServiceTestDelete {
        @Test
        public void delete() {
            AccountHolder expected = new AccountHolder();

            service.delete(expected);
            verify(repository).delete(expected);
        }
    }

    @Nested
    public class AccountHolderServiceTestDeleteById {
        @Test
        public void deleteById() {
            service.deleteById(ID_LONG);
            verify(repository).deleteById(ID_LONG);
        }
    }

}
