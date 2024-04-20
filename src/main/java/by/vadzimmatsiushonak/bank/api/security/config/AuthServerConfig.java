package by.vadzimmatsiushonak.bank.api.security.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
@AllArgsConstructor
public class AuthServerConfig {

    private final KeyManager keyManager;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        JWKSet set = new JWKSet(keyManager.getKey());
        return new ImmutableJWKSet<>(set);
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyManager.getPublicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

}
