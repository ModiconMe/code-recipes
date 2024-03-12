package ru.modiconme.recepies.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SecureRandoms {

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[20];
        secureRandom.nextBytes(bytes);
        int rInt = secureRandom.nextInt();
        long rLong = secureRandom.nextLong();
        double rDouble = secureRandom.nextDouble();

        System.out.println("Bytes: " + Arrays.toString(bytes));
        System.out.println("Int: " + rInt);
        System.out.println("Long: " + rLong);
        System.out.println("Double: " + rDouble);

        String password = secureRandom.ints(30, 'A', 'Z')
                .mapToObj(data -> (char) data)
                .map(Object::toString)
                .collect(Collectors.joining());
        System.out.println(password);

        byte[] seed = secureRandom.generateSeed(128);
        System.out.println("Seed: " + Arrays.toString(seed));

    }
}
