package by.vadzimmatsiushonak.bank.api.security.filter;

import by.vadzimmatsiushonak.bank.api.service.Oauth2TokenStore;
import by.vadzimmatsiushonak.bank.api.util.JwtTokenUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter = new JwtAuthenticationConverter();
    private final BearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();

    private final JwtTokenUtil jwtTokenUtil;
    private final Oauth2TokenStore tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String token;
        try {
            token = this.bearerTokenResolver.resolve(request);
        } catch (OAuth2AuthenticationException invalid) {
            log.trace("Sending to authentication entry point since failed to resolve bearer token",
                invalid);
//            this.authenticationEntryPoint.commence(request, response, invalid);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            String error = "Unauthorized";
            String jsonError = "{\"error\": \"" + error + "\",\"message\": \"" + invalid.getMessage() + "\"}";
            response.getWriter().write(jsonError);
            return;
        }
        if (token == null) {
            log.trace("Did not process request since did not find bearer token");
            filterChain.doFilter(request, response);
            return;
        }

        AbstractAuthenticationToken authenticationResult = null;

        try {
            authenticationResult = authenticate(token);
        } catch (InvalidBearerTokenException invalid) {
            log.trace("Invalid token provided or Failed to find token in store");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            String error = "Unauthorized";
            String jsonError = "{\"error\": \"" + error + "\",\"message\": \"" + invalid.getMessage() + "\"}";
            response.getWriter().write(jsonError);
            return;
        }

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationResult);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }


    public AbstractAuthenticationToken authenticate(String token) {
        Jwt jwt = jwtTokenUtil.getJwt(token);

        if (!tokenService.containsToken(jwt.getId())) {
            throw new InvalidBearerTokenException("Unable to find the token in store or token expired");
        }

        AbstractAuthenticationToken authenticationToken = this.jwtAuthenticationConverter.convert(
            jwt);
        this.logger.debug("Authenticated token");
        return authenticationToken;
    }
}
