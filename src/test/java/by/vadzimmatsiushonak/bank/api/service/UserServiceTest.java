package by.vadzimmatsiushonak.bank.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.TestConstants.ID_LONG;
import static utils.TestConstants.LOGIN_USERNAME;
import static utils.TestConstants.PASSWORD;
import static utils.TestConstants.PHONENUMBER;

import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.UserDetails;
import by.vadzimmatsiushonak.bank.api.repository.UserRepository;
import by.vadzimmatsiushonak.bank.api.service.impl.UserServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Nested
    public class UserServiceTestCreate {

        @Test
        public void save() {
            User expected = new User();
            expected.setId(ID_LONG);
            expected.setPassword(PASSWORD);

            User user = new User();
            user.setPassword(PASSWORD);
            when(encoder.encode(PASSWORD)).thenReturn(PASSWORD);
            when(repository.save(user)).thenReturn(expected);

            User actual = service.save(user);
            assertEquals(expected, actual);
            verify(repository).save(user);
            verify(encoder).encode(PASSWORD);
        }

        @Test
        public void saveWithId() {
            User expected = new User();
            expected.setId(ID_LONG);
            expected.setPassword(PASSWORD);

            User user = new User();
            user.setPassword(PASSWORD);
            when(encoder.encode(PASSWORD)).thenReturn(PASSWORD);
            when(repository.save(user)).thenReturn(expected);

            User userWithId = new User();
            userWithId.setId(ID_LONG);
            userWithId.setPassword(PASSWORD);

            User actual = service.save(userWithId);
            assertEquals(expected, actual);
            verify(repository).save(user);
            verify(encoder).encode(PASSWORD);
        }
    }

    @Nested
    public class UserServiceTestFindById {

        @Test
        public void findById() {
            User user = new User();
            user.setId(ID_LONG);
            when(repository.findById(ID_LONG)).thenReturn(Optional.of(user));

            User actual = service.findById(ID_LONG).orElse(null);
            assertEquals(user, actual);
            verify(repository).findById(ID_LONG);
        }
    }

    @Nested
    public class UserServiceTestFindByUsername {

        @Test
        public void findByUsername() {
            User user = new User();
            user.setLogin(LOGIN_USERNAME);
            when(repository.findByLogin(LOGIN_USERNAME)).thenReturn(Optional.of(user));

            User actual = service.findByLogin(LOGIN_USERNAME).orElse(null);
            assertEquals(user, actual);
            verify(repository).findByLogin(LOGIN_USERNAME);
        }
    }

    @Nested
    public class UserServiceTestFindByPhoneNumber {

        @Test
        public void findByUserDetailsPhoneNumber() {
            UserDetails userDetails = new UserDetails();
            userDetails.setPhoneNumber(PHONENUMBER);
            User user = new User();
            user.setUserDetails(userDetails);
            when(repository.findByUserDetailsPhoneNumber(PHONENUMBER)).thenReturn(Optional.of(user));

            User actual = service.findByPhoneNumber(PHONENUMBER).orElse(null);
            assertEquals(user, actual);
            verify(repository).findByUserDetailsPhoneNumber(PHONENUMBER);
        }
    }

    @Nested
    public class UserServiceTestFindAll {

        @Test
        public void findAll() {
            User user = new User();
            user.setId(ID_LONG);
            List<User> expected = List.of(user);
            when(repository.findAll()).thenReturn(expected);

            List<User> actual = service.findAll();
            assertEquals(expected, actual);
            verify(repository).findAll();
        }
    }

    @Nested
    public class UserServiceTestUpdate {

        @Test
        public void update() {
            User expected = new User();
            expected.setId(ID_LONG);
            when(repository.save(expected)).thenReturn(expected);

            service.update(expected);
            verify(repository).save(expected);
        }

        @Test
        public void updateWithEmptyIban() {
            User expected = new User();

            assertThrows(
                NullPointerException.class,
                () -> service.update(expected));
            verify(repository, times(0)).save(any());
        }
    }

    @Nested
    public class UserServiceTestDelete {

        @Test
        public void delete() {
            User expected = new User();

            service.delete(expected);
            verify(repository).delete(expected);
        }
    }

    @Nested
    public class UserServiceTestDeleteById {

        @Test
        public void deleteById() {
            service.deleteById(ID_LONG);
            verify(repository).deleteById(ID_LONG);
        }
    }
}
