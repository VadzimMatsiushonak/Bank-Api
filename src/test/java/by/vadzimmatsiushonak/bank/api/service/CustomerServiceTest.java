package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import by.vadzimmatsiushonak.bank.api.repository.CustomerRepository;
import by.vadzimmatsiushonak.bank.api.service.impl.CustomerServiceImpl;
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
import static utils.TestConstants.PHONENUMBER;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl service;

    @Mock
    private CustomerRepository repository;

    @Nested
    public class CustomerServiceTestCreate {
        @Test
        public void save() {
            Customer expected = new Customer();
            expected.setId(ID_LONG);

            Customer customer = new Customer();
            when(repository.save(customer)).thenReturn(expected);


            Customer actual = service.save(customer);
            assertEquals(expected, actual);
            verify(repository).save(expected);
        }

        @Test
        public void saveWithId() {
            Customer expected = new Customer();
            expected.setId(ID_LONG);

            Customer customer = new Customer();
            when(repository.save(customer)).thenReturn(expected);

            Customer customerWithId = new Customer();
            customerWithId.setId(ID_LONG);

            Customer actual = service.save(customerWithId);
            assertEquals(expected, actual);
            verify(repository).save(expected);
        }
    }

    @Nested
    public class CustomerServiceTestFindById {
        @Test
        public void findById() {
            Customer customer = new Customer();
            customer.setId(ID_LONG);
            when(repository.findById(ID_LONG)).thenReturn(Optional.of(customer));


            Customer actual = service.findById(ID_LONG).orElse(null);
            assertEquals(customer, actual);
            verify(repository).findById(ID_LONG);
        }
    }

    @Nested
    public class CustomerServiceTestFindByPhoneNumber {
        @Test
        public void findByPhoneNumber() {
            Customer customer = new Customer();
            customer.setPhoneNumber(PHONENUMBER);
            when(repository.findByPhoneNumber(PHONENUMBER)).thenReturn(Optional.of(customer));


            Customer actual = service.findByPhoneNumber(PHONENUMBER).orElse(null);
            assertEquals(customer, actual);
            verify(repository).findByPhoneNumber(PHONENUMBER);
        }
    }

    @Nested
    public class CustomerServiceTestFindAll {
        @Test
        public void findAll() {
            Customer customer = new Customer();
            customer.setId(ID_LONG);
            List<Customer> expected = List.of(customer);
            when(repository.findAll()).thenReturn(expected);


            List<Customer> actual = service.findAll();
            assertEquals(expected, actual);
            verify(repository).findAll();
        }
    }

    @Nested
    public class CustomerServiceTestUpdate {
        @Test
        public void update() {
            Customer expected = new Customer();
            expected.setId(ID_LONG);
            when(repository.save(expected)).thenReturn(expected);


            service.update(expected);
            verify(repository).save(expected);
        }

        @Test
        public void updateWithEmptyIban() {
            Customer expected = new Customer();

            assertThrows(
                    NullPointerException.class,
                    () -> service.update(expected));
            verify(repository, times(0)).save(any());
        }
    }

    @Nested
    public class CustomerServiceTestDelete {
        @Test
        public void delete() {
            Customer expected = new Customer();

            service.delete(expected);
            verify(repository).delete(expected);
        }
    }

    @Nested
    public class CustomerServiceTestDeleteById {
        @Test
        public void deleteById() {
            service.deleteById(ID_LONG);
            verify(repository).deleteById(ID_LONG);
        }
    }
}