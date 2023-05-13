package by.vadzimmatsiushonak.bank.api.util;

import java.util.Random;

public class NumberUtils {

    public static final int VERIFICATION_MIN_VALUE = 1000;
    public static final int VERIFICATION_MAX_VALUE = 9999;

    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

}
