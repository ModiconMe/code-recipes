package ru.modiconme.core.java8.lambdas;

import java.util.function.Consumer;

public class LambdaVariable2 {

    static int value1 = 4;

    public static void main(String[] args) {
        int value2 = 4;

        Consumer<Integer> c1 = (i) -> {
            value1++; // non local
//            value2++; // local var should be final or effectively final
            System.out.println(value1 + value2);
        };

        c1.accept(4);
    }
}
