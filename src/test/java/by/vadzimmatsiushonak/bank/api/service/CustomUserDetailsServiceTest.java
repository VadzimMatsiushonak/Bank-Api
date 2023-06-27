package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.exception.InactiveUserException;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.UserStatus;
import by.vadzimmatsiushonak.bank.api.repository.UserRepository;
import by.vadzimmatsiushonak.bank.api.service.impl.CustomUserDetailsService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.TestConstants.TEST_PASSWORD;
import static utils.TestConstants.TEST_USERNAME;

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
            user.setLogin(TEST_USERNAME);
            user.setPassword(TEST_PASSWORD);
            user.setStatus(UserStatus.ACTIVE);
            user.setRole(Role.ADMIN);

            UserDetails expected = new org.springframework.security.core.userdetails.User(
                    user.getLogin(), user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(user.getRole().authority)));


            when(repository.findByLogin(TEST_USERNAME)).thenReturn(Optional.of(user));


            UserDetails actual = service.loadUserByUsername(TEST_USERNAME);
            assertEquals(expected, actual);
            verify(repository).findByLogin(TEST_USERNAME);
        }

        @Test
        public void loadUserByUsernameUserNotFound() {
            when(repository.findByLogin(TEST_USERNAME)).thenReturn(Optional.empty());

            assertThrows(UsernameNotFoundException.class,
                    () -> service.loadUserByUsername(TEST_USERNAME));

            verify(repository).findByLogin(TEST_USERNAME);
        }

        @Test
        public void loadUserByUsernameInactiveUser() {
            User user = new User();
            user.setStatus(UserStatus.DISABLED);

            when(repository.findByLogin(TEST_USERNAME)).thenReturn(Optional.of(user));

            assertThrows(InactiveUserException.class,
                    () -> service.loadUserByUsername(TEST_USERNAME));

            verify(repository).findByLogin(TEST_USERNAME);
        }
    }
}