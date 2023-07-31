package by.vadzimmatsiushonak.bank.api.facade;

import by.vadzimmatsiushonak.bank.api.exception.*;
import by.vadzimmatsiushonak.bank.api.facade.impl.AuthorizationFacadeImpl;
import by.vadzimmatsiushonak.bank.api.model.Confirmation;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.UserStatus;
import by.vadzimmatsiushonak.bank.api.service.ConfirmationService;
import by.vadzimmatsiushonak.bank.api.service.Oauth2TokenStore;
import by.vadzimmatsiushonak.bank.api.service.UserService;
import by.vadzimmatsiushonak.bank.api.util.JwtTokenUtil;
import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static by.vadzimmatsiushonak.bank.api.constant.MetadataConstants.ID;
import static by.vadzimmatsiushonak.bank.api.facade.impl.AuthorizationFacadeImpl.LOGIN_KEY;
import static by.vadzimmatsiushonak.bank.api.facade.impl.AuthorizationFacadeImpl.REGISTRATION_KEY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static utils.TestConstants.*;

@ExtendWith(MockitoExtension.class)
public class AuthorizationFacadeTest {

    @InjectMocks
    @Spy
    private AuthorizationFacadeImpl facade;

    @Mock
    private ConfirmationService confirmationService;
    @Mock
    private UserService userServices;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private Oauth2TokenStore tokenService;

    @Nested
    public class AuthorizationFacadeTestAuthenticate {

        @Test
        public void authenticateAbsentUser() {
            when(userServices.findByUsername(USERNAME)).thenReturn(Optional.empty());


            assertThrows(UserNotFoundException.class,
                    () -> facade.authenticate(USERNAME, PASSWORD));
        }

        @Test
        public void authenticateWrongPassword() {
            User user = new User();
            user.setPassword(PASSWORD);


            when(userServices.findByUsername(USERNAME)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(WRONG_PASSWORD, PASSWORD)).thenReturn(false);


            assertThrows(InvalidCredentialsException.class,
                    () -> facade.authenticate(USERNAME, WRONG_PASSWORD));
        }

        @Test
        public void authenticate() {
            String expected = CODE;
            User user = new User();
            user.setLogin(USERNAME);
            user.setPassword(PASSWORD);


            when(userServices.findByUsername(USERNAME)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(PASSWORD, PASSWORD)).thenReturn(true);
            when(confirmationService.generateCode(Map.of(LOGIN, user.getLogin()), LOGIN_KEY)).thenReturn(expected);

            System.out.println(expected);

            String actual = facade.authenticate(USERNAME, PASSWORD);
            assertEquals(expected, actual);
        }

    }


    @Nested
    public class AuthorizationFacadeTestGetToken {

        @Test
        public void getToken() {
            Confirmation verification = new Confirmation(CODE_INT, Map.of(LOGIN, USERNAME));
            org.springframework.security.core.userdetails.User userDetails =
                    new org.springframework.security.core.userdetails.User(USERNAME, PASSWORD,
                            Collections.emptyList());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    new UserPrincipal(USERNAME), null, Collections.emptyList());
            Jwt jwt = mock(Jwt.class);


            doReturn(verification).when(confirmationService).confirmCode(KEY, CODE_INT);
            when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
            when(jwtTokenUtil.generateJwtToken(auth)).thenReturn(jwt);
            when(jwt.getTokenValue()).thenReturn(TOKEN);


            String actual = facade.getToken(KEY, CODE_INT);

            assertEquals(TOKEN, actual);
            verify(userDetailsService).loadUserByUsername(USERNAME);
            verify(jwtTokenUtil).generateJwtToken(auth);
            verify(tokenService).save(jwt);
        }

    }

    @Nested
    public class AuthorizationFacadeTestRevokeToken {

        @Test
        public void revokeInvalidToken() {
            when(jwtTokenUtil.getJwt(TOKEN)).thenThrow(new RuntimeException());


            assertThrows(BadRequestException.class,
                    () -> facade.revokeToken(TOKEN));
            verify(jwtTokenUtil).getJwt(TOKEN);
            verify(tokenService, times(0)).removeById(any());
        }

        @Test
        public void revokeExistingToken() {
            Jwt jwt = mock(Jwt.class);


            when(jwt.getId()).thenReturn(ID);
            when(jwtTokenUtil.getJwt(TOKEN)).thenReturn(jwt);
            when(tokenService.removeById(ID)).thenReturn(true);


            assertTrue(facade.revokeToken(TOKEN));
            verify(jwtTokenUtil).getJwt(TOKEN);
            verify(tokenService).removeById(ID);
        }

        @Test
        public void revokeAbsentToken() {
            Jwt jwt = mock(Jwt.class);


            when(jwt.getId()).thenReturn(ID);
            when(jwtTokenUtil.getJwt(TOKEN)).thenReturn(jwt);
            when(tokenService.removeById(ID)).thenReturn(false);


            assertFalse(facade.revokeToken(TOKEN));
            verify(jwtTokenUtil).getJwt(TOKEN);
            verify(tokenService).removeById(ID);
        }

    }


    @Nested
    public class AuthorizationFacadeTestRegister {

        @Test
        public void register() {
            User user = new User();
            user.setId(ID_LONG);
            user.setRole(Role.TECHNICAL_USER);


            doReturn(CODE).when(confirmationService).generateCode(Map.of(ID, user.getId()), REGISTRATION_KEY);


            String actual = facade.register(user);
            assertEquals(CODE, actual);
            verify(userServices).save(user);
        }

    }


    @Nested
    public class AuthorizationFacadeTestVerifyRegistration {

        @Test
        public void verifyRegistration() {
            Confirmation confirmation = new Confirmation(CODE_INT, Map.of(ID, ID_LONG));
            User user = new User();
            user.setId(ID_LONG);
            user.setStatus(UserStatus.DISABLED);


            doReturn(confirmation).when(confirmationService).confirmCode(KEY, CODE_INT);
            when(userServices.findById(ID_LONG)).thenReturn(Optional.of(user));


            assertTrue(facade.confirmRegistration(KEY, CODE_INT));
            assertEquals(UserStatus.ACTIVE, user.getStatus());
            verify(userServices).findById(ID_LONG);
        }

        @Test
        public void verifyRegistrationAbsentUser() {
            Confirmation confirmation = new Confirmation(CODE_INT, Map.of(ID, ID_LONG, LOGIN, USERNAME));


            doReturn(confirmation).when(confirmationService).confirmCode(KEY, CODE_INT);
            when(userServices.findById(ID_LONG)).thenReturn(Optional.empty());


            assertThrows(EntityNotFoundException.class,
                    () -> facade.confirmRegistration(KEY, CODE_INT));
            verify(userServices).findById(ID_LONG);
        }

    }

}
