package by.vadzimmatsiushonak.bank.api.security.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthServerConfig {

    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final Set<String> REDIRECT_URIS;

    private final KeyManager keyManager;

    public AuthServerConfig(@Value("${bank-api.security.client-id}") String CLIENT_ID,
        @Value("${bank-api.security.client-secret}") String CLIENT_SECRET,
        @Value("${bank-api.security.redirect-uris}") Set<String> REDIRECT_URIS,
        KeyManager keyManager) {
        this.CLIENT_ID = CLIENT_ID;
        this.CLIENT_SECRET = CLIENT_SECRET;
        this.REDIRECT_URIS = REDIRECT_URIS;
        this.keyManager = keyManager;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        return http
            .formLogin().and()
            .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient repository = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId(CLIENT_ID)
            .clientSecret(CLIENT_SECRET)
            .authorizationGrantTypes(types -> {
                types.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                types.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
            })
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .scope(OidcScopes.OPENID)
            .redirectUris(uris -> uris.addAll(REDIRECT_URIS))
            .build();

        return new InMemoryRegisteredClientRepository(repository);
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        JWKSet set = new JWKSet(keyManager.rsaKey());

        return (j, sc) -> j.select(set);
    }

}
