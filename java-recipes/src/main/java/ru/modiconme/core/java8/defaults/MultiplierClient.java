package ru.modiconme.core.java8.defaults;

import java.util.Arrays;
import java.util.List;

public class MultiplierClient {

    public static void main(String[] args) {
        Multiplier multiplier = new MultiplierImpl();
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Result: " + multiplier.multiply(integers));
        System.out.println("Result of size: " + multiplier.size(integers));
        System.out.println("Result of isEmpty: " + Multiplier.isEmpty(integers));
    }
}
