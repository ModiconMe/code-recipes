package ru.modiconme.core.java8.lambdas;

import java.util.function.Consumer;

public class LambdaVariable1 {

    public static void main(String[] args) {
        int i = 0;

//        Consumer<Integer> c1 = (i) -> { // already defined in this scope
        Consumer<Integer> c1 = (i1) -> {
//            int i  = 10; // already defined in this scope
            System.out.println("Value id = " + i); // local var
            System.out.println("Value id = " + i1);
        };
    }
}
