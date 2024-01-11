package by.vadzimmatsiushonak.bank.api.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtils {

    /**
     * Returns the login of the current authenticated user.
     * @return the login of the current authenticated user, as a String.
     * @throws NullPointerException if the current authentication context is null.
     * @see by.vadzimmatsiushonak.bank.api.service.impl.CustomUserDetailsService#loadUserByUsername(String) The place where we set data in auth
     */
    public static String getAuthLogin() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return auth.getName();
        } catch (NullPointerException e) {
            throw new NullPointerException("Authentication context is null.");
        }
    }
}
