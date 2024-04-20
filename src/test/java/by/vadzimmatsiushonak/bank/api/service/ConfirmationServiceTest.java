package by.vadzimmatsiushonak.bank.api.service;

import static by.vadzimmatsiushonak.bank.api.facade.impl.AuthorizationFacadeImpl.LOGIN_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.TestConstants.CODE_INT;
import static utils.TestConstants.ID;
import static utils.TestConstants.ID_LONG;
import static utils.TestConstants.KEY;
import static utils.TestConstants.LOGIN;
import static utils.TestConstants.LOGIN_USERNAME;
import static utils.TestConstants.WRONG_CODE_INT;

import by.vadzimmatsiushonak.bank.api.exception.ConfirmationNotFoundException;
import by.vadzimmatsiushonak.bank.api.exception.InvalidConfirmationException;
import by.vadzimmatsiushonak.bank.api.model.Confirmation;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.service.impl.ConfirmationServiceImpl;
import by.vadzimmatsiushonak.bank.api.util.NumberUtils;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;

@ExtendWith(MockitoExtension.class)
class ConfirmationServiceTest {

    @InjectMocks
    private ConfirmationServiceImpl confirmationService;
    @Mock
    private Cache confirmationCache;
    @Mock
    private NumberUtils numberUtils;

    @Nested
    public class AuthorizationFacadeTestGenerateCode {

        @Test
        public void generateCode() {
            User user = new User();
            user.setId(ID_LONG);
            user.setLogin(LOGIN_USERNAME);
            Map<Object, Object> metadata = Map.of(ID, ID_LONG, LOGIN, LOGIN_USERNAME);
            String key = confirmationService.generateCode(metadata, LOGIN_KEY);

            assertTrue(key.startsWith(LOGIN_KEY));
            verify(confirmationCache).put(any(),
                new Confirmation(any(), metadata));
        }

    }

    @Nested
    public class AuthorizationFacadeTestVerifyCode {

        @Test
        public void confirmCode() {
            Confirmation expected = new Confirmation(CODE_INT, Map.of(ID, ID_LONG, LOGIN, LOGIN_USERNAME));

            when(confirmationCache.get(KEY, Confirmation.class)).thenReturn(expected);

            Confirmation actual = confirmationService.confirmCode(KEY, CODE_INT);
            assertEquals(expected, actual);
            verify(confirmationCache).evictIfPresent(KEY);
        }

        @Test
        public void confirmCodeAbsentVerification() {
            when(confirmationCache.get(KEY, Confirmation.class)).thenReturn(null);

            assertThrows(ConfirmationNotFoundException.class,
                () -> confirmationService.confirmCode(KEY, CODE_INT));
            verify(confirmationCache, times(0)).evictIfPresent(any());
        }

        @Test
        public void confirmCodeWrongInputCode() {
            Confirmation confirmation = new Confirmation(CODE_INT, Map.of(ID, ID_LONG, LOGIN, LOGIN_USERNAME));

            when(confirmationCache.get(KEY, Confirmation.class)).thenReturn(confirmation);

            assertThrows(InvalidConfirmationException.class,
                () -> confirmationService.confirmCode(KEY, WRONG_CODE_INT));
            verify(confirmationCache, times(0)).evictIfPresent(any());
        }

    }

}
