package by.vadzimmatsiushonak.bank.api.util;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EnvironmentUtils {

    private final Environment environment;

    public boolean isProd() {
        return isActiveProfile("prod");
    }

    public boolean isActiveProfile(String profileName) {
        return environment.getActiveProfiles().length > 0 && Arrays.asList(environment.getActiveProfiles())
            .contains(profileName);
    }
}