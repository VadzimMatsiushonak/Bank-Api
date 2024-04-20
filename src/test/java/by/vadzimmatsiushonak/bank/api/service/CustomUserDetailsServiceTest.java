package by.vadzimmatsiushonak.bank.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.TestConstants.LOGIN_USERNAME;
import static utils.TestConstants.PASSWORD;

import by.vadzimmatsiushonak.bank.api.exception.InactiveUserException;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus;
import by.vadzimmatsiushonak.bank.api.repository.UserRepository;
import by.vadzimmatsiushonak.bank.api.service.impl.CustomUserDetailsService;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService service;

    @Mock
    private UserRepository repository;

    @Nested
    public class CustomUserDetailsServiceTestLoadUserByUsername {

        @Test
        public void loadUserByUsername() {
            User user = new User();
            user.setLogin(LOGIN_USERNAME);
            user.setPassword(PASSWORD);
            user.setStatus(ModelStatus.ACTIVE);
            user.setRole(Role.ADMIN);

            UserDetails expected = new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().authority)));

            when(repository.findByLogin(LOGIN_USERNAME)).thenReturn(Optional.of(user));

            UserDetails actual = service.loadUserByUsername(LOGIN_USERNAME);
            assertEquals(expected, actual);
            verify(repository).findByLogin(LOGIN_USERNAME);
        }

        @Test
        public void loadUserByUsernameUserNotFound() {
            when(repository.findByLogin(LOGIN_USERNAME)).thenReturn(Optional.empty());

            assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername(LOGIN_USERNAME));

            verify(repository).findByLogin(LOGIN_USERNAME);
        }

        @Test
        public void loadUserByUsernameInactiveUser() {
            User user = new User();
            user.setStatus(ModelStatus.INACTIVE);

            when(repository.findByLogin(LOGIN_USERNAME)).thenReturn(Optional.of(user));

            assertThrows(InactiveUserException.class,
                () -> service.loadUserByUsername(LOGIN_USERNAME));

            verify(repository).findByLogin(LOGIN_USERNAME);
        }
    }
}
