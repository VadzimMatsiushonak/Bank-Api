package by.vadzimmatsiushonak.bank.api.service.impl;

import static by.vadzimmatsiushonak.bank.api.model.entity.base.ModelStatus.ACTIVE;
import static by.vadzimmatsiushonak.bank.api.util.ExceptionUtils.new_InactiveUserException;

import by.vadzimmatsiushonak.bank.api.exception.InactiveUserException;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.repository.UserRepository;
import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@Validated
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository repository;

    /**
     * Finds a User object using the provided username.
     *
     * @param username The username of the user to load.
     * @return A UserDetails object representing the loaded user.
     * @throws UsernameNotFoundException If the user cannot be found using the provided username.
     * @throws InactiveUserException     If the user account is not activated.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByLogin(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not found"));
        if (!ACTIVE.equals(user.getStatus())) {
            throw new_InactiveUserException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
            user.getPassword(),
            getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().authority));
    }
}
