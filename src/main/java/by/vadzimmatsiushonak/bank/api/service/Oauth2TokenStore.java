package by.vadzimmatsiushonak.bank.api.service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.jwt.Jwt;

public interface Oauth2TokenStore {

    void save(@NotNull Jwt jwt);

    boolean removeById(@NotBlank String id);

    void removeByToken(@NotBlank String token);

    @Nullable
    Jwt findById(@NotBlank String id);

    @Nullable
    Jwt findByToken(@NotBlank String token);

    boolean containsToken(@NotBlank String id);
}
