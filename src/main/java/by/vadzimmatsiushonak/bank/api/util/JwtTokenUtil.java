package by.vadzimmatsiushonak.bank.api.util;

import static by.vadzimmatsiushonak.bank.api.model.entity.auth.Role.SCOPE_PREFIX;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtTokenUtil {

    private final String issuer;
    private final Integer expiryMinutes;

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public JwtTokenUtil(JwtEncoder encoder, JwtDecoder decoder,
        @Value("${bank-api.security.issuer}") String issuer,
        @Value("${bank-api.security.expiry-minutes}") Integer expiryMinutes) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.issuer = issuer;
        this.expiryMinutes = expiryMinutes;
    }

    public Jwt getJwt(String token) {
        try {
            return this.decoder.decode(token);
        } catch (BadJwtException failed) {
            log.debug("Failed to authenticate since the JWT was invalid");
            throw new InvalidBearerTokenException(failed.getMessage(), failed);
        } catch (JwtException failed) {
            throw new AuthenticationServiceException(failed.getMessage(), failed);
        }
    }

    public Jwt generateJwtToken(Authentication authentication) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(expiryMinutes, ChronoUnit.MINUTES);
        // @formatter:off
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                // Removing SCOPE_PREFIX, since it will be added at .claim builder
                .map(auth -> auth.replace(SCOPE_PREFIX, ""))
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuer(issuer)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .subject(authentication.getName())
                // If we adding "SCOPE" then in our authority will be "SCOPE_" + authority
                .claim(OAuth2ParameterNames.SCOPE, scope)
                .build();
        JwsHeader headers = JwsHeader.with(SignatureAlgorithm.RS256).build();
        // @formatter:on
        return this.encoder.encode(JwtEncoderParameters.from(headers, claims));
    }
}
