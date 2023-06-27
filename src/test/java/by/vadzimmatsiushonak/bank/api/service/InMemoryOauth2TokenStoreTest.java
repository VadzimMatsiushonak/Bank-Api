package by.vadzimmatsiushonak.bank.api.service;

import by.vadzimmatsiushonak.bank.api.service.impl.InMemoryOauth2TokenStore;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static utils.TestConstants.TEST_ID_STRING;
import static utils.TestConstants.TEST_TOKEN;

@ExtendWith(MockitoExtension.class)
public class InMemoryOauth2TokenStoreTest {

    private final InMemoryOauth2TokenStore tokenStore;

    private final Map<String, Jwt> store;

    public InMemoryOauth2TokenStoreTest() {
        this.store = mock(Map.class);
        this.tokenStore = spy(new InMemoryOauth2TokenStore(Optional.of(store)));
    }

    @Nested
    public class InMemoryOauth2TokenStoreTestSave {
        @Test
        public void save() {
            Jwt jwt = mock(Jwt.class);
            when(jwt.getId()).thenReturn(TEST_ID_STRING);
            when(store.put(jwt.getId(), jwt)).thenReturn(jwt);

            tokenStore.save(jwt);
            verify(store).put(jwt.getId(), jwt);
        }
    }

    @Nested
    public class InMemoryOauth2TokenStoreTestRemoveById {
        @Test
        public void removeByIdExisting() {
            Jwt existing = mock(Jwt.class);
            when(store.remove(TEST_ID_STRING)).thenReturn(existing);

            assertTrue(tokenStore.removeById(TEST_ID_STRING));
            verify(store).remove(TEST_ID_STRING);
        }

        @Test
        public void removeByIdNonExisting() {
            when(store.remove(TEST_ID_STRING)).thenReturn(null);

            assertFalse(tokenStore.removeById(TEST_ID_STRING));
            verify(store).remove(TEST_ID_STRING);
        }
    }

    @Nested
    public class InMemoryOauth2TokenStoreTestRemoveByToken {
        @Test
        public void removeByToken() {
            tokenStore.removeByToken(TEST_TOKEN);
        }
    }

    @Nested
    public class InMemoryOauth2TokenStoreTestFindById {
        @Test
        public void findById() {
            Jwt expected = mock(Jwt.class);
            when(store.get(TEST_ID_STRING)).thenReturn(expected);

            Jwt actual = tokenStore.findById(TEST_ID_STRING);
            assertEquals(expected, actual);
            verify(store).get(TEST_ID_STRING);
        }
    }

    @Nested
    public class InMemoryOauth2TokenStoreTestFindByToken {
        @Test
        public void findByToken() {
            Jwt expected = mock(Jwt.class);
            when(expected.getTokenValue()).thenReturn(TEST_TOKEN);
            when(store.values()).thenReturn(Collections.singleton(expected));

            Jwt actual = tokenStore.findByToken(TEST_TOKEN);
            assertEquals(expected, actual);
            verify(store).values();
        }
    }

    @Nested
    public class InMemoryOauth2TokenStoreTestContainsToken {
        @Test
        public void containsToken() {
            when(store.containsKey(TEST_ID_STRING)).thenReturn(true);

            assertTrue(tokenStore.containsToken(TEST_ID_STRING));
            verify(store).containsKey(TEST_ID_STRING);
        }
    }
}