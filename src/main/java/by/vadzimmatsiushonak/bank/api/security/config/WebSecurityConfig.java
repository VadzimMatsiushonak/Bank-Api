package by.vadzimmatsiushonak.bank.api.security.config;

import by.vadzimmatsiushonak.bank.api.security.filter.JwtTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

import static by.vadzimmatsiushonak.bank.api.model.entity.auth.Role.ADMIN;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        configureCors(http);
        enableOauth2ResourceServer(http);
        protectApiEndpoints(http);

        // Temporary disabled CSRF
        // TODO fix the issue with CSRF
        http.csrf().disable();

        // Add JWT authorization filter
        http.addFilterAt(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.formLogin().and().build();
    }

    public void enableOauth2ResourceServer(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(j -> j.jwt());
    }

    public void configureCors(HttpSecurity http) throws Exception {
        http.cors(c -> {
            CorsConfigurationSource source = s -> {
                CorsConfiguration cc = new CorsConfiguration();
                cc.setAllowCredentials(true);
//                cc.setAllowedOrigins(List.of("http://127.0.0.1:3000"));
                cc.setAllowedOrigins(List.of("*"));
                cc.setAllowedHeaders(List.of("*"));
                cc.setAllowedMethods(List.of("*"));
                return cc;
            };

            c.configurationSource(source);
        });
    }

    public void protectApiEndpoints(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // Authorization endpoints
                .antMatchers(HttpMethod.POST, "/oauth2/**").permitAll()
                // Protected API resources
                .antMatchers(HttpMethod.GET, "/api/**").authenticated()
                // Protected API modification endpoints
                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(ADMIN.authority)
                .antMatchers(HttpMethod.PUT, "/api/**").hasAuthority(ADMIN.authority)
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(ADMIN.authority)
                .anyRequest().authenticated();

        protectSwaggerEndpoints(http);
        protectH2ConsoleEndpoints(http);
    }

    public void protectH2ConsoleEndpoints(HttpSecurity http) throws Exception {
        // this may not be required, depends on your app configuration
        http.authorizeRequests()
                // we need config just for console, nothing else
                .antMatchers("/h2-console/**").hasAuthority(ADMIN.authority);
        // this will ignore only h2-console csrf, spring security 4+
        http.csrf().ignoringAntMatchers("/h2-console/**");
        //this will allow frames with same origin which is much more safe
        http.headers().frameOptions().sameOrigin();
    }

    public void protectSwaggerEndpoints(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/swagger-ui/**").hasAuthority(ADMIN.authority)
                .antMatchers("/v2/**").hasAuthority(ADMIN.authority);
    }

}
