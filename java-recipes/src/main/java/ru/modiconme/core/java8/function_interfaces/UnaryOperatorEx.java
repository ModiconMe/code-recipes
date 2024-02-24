package ru.modiconme.core.java8.function_interfaces;

import java.util.function.UnaryOperator;

public class UnaryOperatorEx {

    // = Function<T, T>
    static UnaryOperator<String> unaryOperator = (s) -> s.concat("Default");

    public static void main(String[] args) {
        System.out.println(unaryOperator.apply("java8 "));
    }
}
