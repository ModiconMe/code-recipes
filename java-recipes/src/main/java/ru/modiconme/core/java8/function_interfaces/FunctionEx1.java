package ru.modiconme.core.java8.function_interfaces;

public class FunctionEx1 {

    public static String performConcat(String s) {
        return FunctionEx.addSomeString.apply(s);
    }

    public static void main(String[] args) {
        String result = performConcat("Hello");
        System.out.println("Result: " + result);
    }
}
