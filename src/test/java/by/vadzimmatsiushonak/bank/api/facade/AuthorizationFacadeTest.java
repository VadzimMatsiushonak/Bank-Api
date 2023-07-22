package by.vadzimmatsiushonak.bank.api.facade;

import by.vadzimmatsiushonak.bank.api.exception.*;
import by.vadzimmatsiushonak.bank.api.facade.impl.AuthorizationFacadeImpl;
import by.vadzimmatsiushonak.bank.api.model.UserVerification;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.model.entity.auth.Role;
import by.vadzimmatsiushonak.bank.api.model.entity.base.UserStatus;
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
import java.util.Optional;

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
    private UserService userServices;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private Cache verificationCache;
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
            String expected = "code";
            User user = new User();
            user.setPassword(PASSWORD);


            when(userServices.findByUsername(USERNAME)).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(PASSWORD, PASSWORD)).thenReturn(true);
            doReturn(expected).when(facade).generateCode(user, LOGIN_KEY);


            String actual = facade.authenticate(USERNAME, PASSWORD);
            assertEquals(expected, actual);
        }

    }


    @Nested
    public class AuthorizationFacadeTestGetToken {

        @Test
        public void getToken() {
            UserVerification verification = new UserVerification(ID_LONG, USERNAME, CODE_INT);
            org.springframework.security.core.userdetails.User userDetails =
                    new org.springframework.security.core.userdetails.User(USERNAME, PASSWORD,
                            Collections.emptyList());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    new UserPrincipal(USERNAME), null, Collections.emptyList());
            Jwt jwt = mock(Jwt.class);


            doReturn(verification).when(facade).verifyCode(KEY, CODE_INT);
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
            user.setRole(Role.TECHNICAL_USER);


            doReturn(CODE).when(facade).generateCode(user, REGISTRATION_KEY);


            String actual = facade.register(user);
            assertEquals(CODE, actual);
            verify(userServices).save(user);
        }

    }


    @Nested
    public class AuthorizationFacadeTestVerifyRegistration {

        @Test
        public void verifyRegistration() {
            UserVerification verification = new UserVerification(ID_LONG, USERNAME, CODE_INT);
            User user = new User();
            user.setStatus(UserStatus.DISABLED);


            doReturn(verification).when(facade).verifyCode(KEY, CODE_INT);
            when(userServices.findById(ID_LONG)).thenReturn(Optional.of(user));


            assertTrue(facade.verifyRegistration(KEY, CODE_INT));
            assertEquals(UserStatus.ACTIVE, user.getStatus());
            verify(userServices).findById(ID_LONG);
        }

        @Test
        public void verifyRegistrationAbsentUser() {
            UserVerification verification = new UserVerification(ID_LONG, USERNAME, CODE_INT);


            doReturn(verification).when(facade).verifyCode(KEY, CODE_INT);
            when(userServices.findById(ID_LONG)).thenReturn(Optional.empty());


            assertThrows(EntityNotFoundException.class,
                    () -> facade.verifyRegistration(KEY, CODE_INT));
            verify(userServices).findById(ID_LONG);
        }

    }

    @Nested
    public class AuthorizationFacadeTestGenerateCode {

        @Test
        public void generateCode() {
            User user = new User();
            user.setId(ID_LONG);
            user.setLogin(USERNAME);
            String key = facade.generateCode(user, LOGIN_KEY);


            assertTrue(key.startsWith(LOGIN_KEY));
            verify(verificationCache).put(any(),
                    new UserVerification(ID_LONG, USERNAME, any()));
        }

    }

    @Nested
    public class AuthorizationFacadeTestVerifyCode {

        @Test
        public void verifyCode() {
            UserVerification expected = new UserVerification(ID_LONG, USERNAME, CODE_INT);


            when(verificationCache.get(KEY, UserVerification.class)).thenReturn(expected);


            UserVerification actual = facade.verifyCode(KEY, CODE_INT);
            assertEquals(expected, actual);
            verify(verificationCache).evictIfPresent(KEY);
        }

        @Test
        public void verifyCodeAbsentVerification() {
            when(verificationCache.get(KEY, UserVerification.class)).thenReturn(null);


            assertThrows(VerificationNotFoundException.class,
                    () -> facade.verifyCode(KEY, CODE_INT));
            verify(verificationCache, times(0)).evictIfPresent(any());
        }

        @Test
        public void verifyCodeWrongInputCode() {
            UserVerification verification = new UserVerification(ID_LONG, USERNAME, CODE_INT);


            when(verificationCache.get(KEY, UserVerification.class)).thenReturn(verification);


            assertThrows(InvalidVerificationException.class,
                    () -> facade.verifyCode(KEY, WRONG_CODE_INT));
            verify(verificationCache, times(0)).evictIfPresent(any());
        }

    }

}
