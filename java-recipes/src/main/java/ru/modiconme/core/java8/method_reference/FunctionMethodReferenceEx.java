package ru.modiconme.core.java8.method_reference;

import java.util.function.Function;

public class FunctionMethodReferenceEx {

    static Function<String, String> toUpperCaseLambda1 = s -> s.toUpperCase();
    static Function<String, String> toUpperCaseLambda2 = String::toUpperCase;

    public static void main(String[] args) {
        System.out.println(toUpperCaseLambda1.apply("string"));
        System.out.println(toUpperCaseLambda2.apply("string"));
    }

}
