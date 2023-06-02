package by.vadzimmatsiushonak.bank.api.security.filter;

import by.vadzimmatsiushonak.bank.api.security.converter.CustomAuthenticationConverter;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEndpointFilter extends OncePerRequestFilter {
    /**
     * The default endpoint {@code URI} for authentication requests.
     */
    private static final String DEFAULT_AUTHENTICATION_ENDPOINT_URI = "/oauth2/authenticate";
    private static final String DEFAULT_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    private final RequestMatcher endpointMatcher;
    private final AuthenticationConverter authenticationConverter;
    private final AuthenticationProvider authenticationProvider = null;
    private final HttpMessageConverter<String> responseConverter =
            new StringHttpMessageConverter();

    public CustomAuthenticationEndpointFilter() {
        this.endpointMatcher = new AntPathRequestMatcher(DEFAULT_AUTHENTICATION_ENDPOINT_URI,
                HttpMethod.POST.name());
        this.authenticationConverter = new CustomAuthenticationConverter();
    }

    private static void throwError(String errorCode, String parameterName) {
        OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName,
                DEFAULT_ERROR_URI);
        throw new OAuth2AuthenticationException(error);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (!this.endpointMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String[] grantTypes = request.getParameterValues(OAuth2ParameterNames.GRANT_TYPE);
            if (grantTypes == null || grantTypes.length != 1) {
                throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.GRANT_TYPE);
            }

            Authentication authorizationGrantAuthentication = this.authenticationConverter.convert(
                    request);
            OAuth2ClientAuthenticationToken accessTokenAuthentication =
                    (OAuth2ClientAuthenticationToken) this.authenticationProvider.authenticate(
                            authorizationGrantAuthentication);
//            this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, accessTokenAuthentication);
        } catch (OAuth2AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(LogMessage.format("Token request failed: %s", ex.getError()), ex);
            }
//            this.authenticationFailureHandler.onAuthenticationFailure(request, response, ex);
        }


    }
}
