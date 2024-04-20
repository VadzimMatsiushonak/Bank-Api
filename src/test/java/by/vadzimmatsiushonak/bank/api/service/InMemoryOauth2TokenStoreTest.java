package by.vadzimmatsiushonak.bank.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.TestConstants.ID;
import static utils.TestConstants.TOKEN;

import by.vadzimmatsiushonak.bank.api.service.impl.InMemoryOauth2TokenStore;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;

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
            when(jwt.getId()).thenReturn(ID);
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
            when(store.remove(ID)).thenReturn(existing);

            assertTrue(tokenStore.removeById(ID));
            verify(store).remove(ID);
        }

        @Test
        public void removeByIdNonExisting() {
            when(store.remove(ID)).thenReturn(null);

            assertFalse(tokenStore.removeById(ID));
            verify(store).remove(ID);
        }
    }

    @Nested
    public class InMemoryOauth2TokenStoreTestRemoveByToken {

        @Test
        public void removeByToken() {
            tokenStore.removeByToken(TOKEN);
        }
    }

    @Nested
    public class InMemoryOauth2TokenStoreTestFindById {

        @Test
        public void findById() {
            Jwt expected = mock(Jwt.class);
            when(store.get(ID)).thenReturn(expected);

            Jwt actual = tokenStore.findById(ID);
            assertEquals(expected, actual);
            verify(store).get(ID);
        }
    }

    @Nested
    public class InMemoryOauth2TokenStoreTestFindByToken {

        @Test
        public void findByToken() {
            Jwt expected = mock(Jwt.class);
            when(expected.getTokenValue()).thenReturn(TOKEN);
            when(store.values()).thenReturn(Collections.singleton(expected));

            Jwt actual = tokenStore.findByToken(TOKEN);
            assertEquals(expected, actual);
            verify(store).values();
        }
    }

    @Nested
    public class InMemoryOauth2TokenStoreTestContainsToken {

        @Test
        public void containsToken() {
            when(store.containsKey(ID)).thenReturn(true);

            assertTrue(tokenStore.containsToken(ID));
            verify(store).containsKey(ID);
        }
    }
}