package by.vadzimmatsiushonak.bank.api.service.impl;

import by.vadzimmatsiushonak.bank.api.service.Oauth2TokenStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class InMemoryOauth2TokenStore implements Oauth2TokenStore {

    private final Map<String, Jwt> store;

    public InMemoryOauth2TokenStore(Optional<Map<String, Jwt>> store) {
        this.store = store.orElse(new ConcurrentHashMap<>());
    }

    @Override
    public void save(@NotNull Jwt jwt) {
        // TODO: Verify that there is no token with specified id before
        this.store.put(jwt.getId(), jwt);
    }

    @Override
    public boolean removeById(@NotBlank String id) {
        Jwt previous = this.store.remove(id);
        return previous != null;
    }

    @Override
    public void removeByToken(@NotNull String token) {
        store.values().removeIf(j -> token.equals(j.getTokenValue()));
    }

    @Nullable
    @Override
    public Jwt findById(@NotBlank String id) {
        return this.store.get(id);
    }

    @Nullable
    @Override
    public Jwt findByToken(@NotBlank String token) {
        return this.store.values().stream()
                .filter(j -> token.equals(j.getTokenValue())).findFirst()
                .orElse(null);
    }

    @Override
    public boolean containsToken(String id) {
        return this.store.containsKey(id);
    }
}
