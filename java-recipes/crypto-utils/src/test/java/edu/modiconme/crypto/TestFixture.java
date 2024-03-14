package edu.modiconme.crypto;

import java.util.Random;

public class TestFixture {

    public static String uniqString() {
        Random random = new Random();
        int length = random.nextInt(100, 200);
        String alphabet = "1234567890qwertyuiopasdfghjklzxcvbnm";
        if (length < 0) {
            throw new IllegalArgumentException();
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return result.toString();
    }
}
