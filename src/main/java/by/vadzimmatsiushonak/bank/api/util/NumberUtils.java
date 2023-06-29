package by.vadzimmatsiushonak.bank.api.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class NumberUtils {

    public static final int VERIFICATION_MIN_VALUE = 1000;
    public static final int VERIFICATION_MAX_VALUE = 9999;

    public static int getRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
