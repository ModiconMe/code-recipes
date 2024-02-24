package ru.modiconme.core.java8.imperativevsdeclarative;

import java.util.stream.IntStream;

public class ImperativeVsDeclarativeEx1 {

    public static void main(String[] args) {

        /**
         * Imperative - how
         */

        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum += i;
        }

        System.out.println("Sum using Imperative approach: " + sum);

        /**
         * Declarative - what
         */
        int sum1 = IntStream.rangeClosed(0, 100)
                .parallel() // it splits the values
                .sum();
        System.out.println("Sum using Declarative approach: " + sum);

    }
}
