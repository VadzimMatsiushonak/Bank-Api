package by.vadzimmatsiushonak.bank.api.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;


@AllArgsConstructor
@Component
public class NumberUtils {

    public static final int CONFIRMATION_MIN_VALUE = 1000;
    public static final int CONFIRMATION_MAX_VALUE = 9999;
    private final EnvironmentUtils environmentUtils;

    public int getRandom(int min, int max) {
        if (environmentUtils.isProd()) {
            return ThreadLocalRandom.current().nextInt(min, max + 1);
        }
        return 1111;
    }

}
