package ru.modiconme.core.java8.numeric_streams;

import ru.modiconme.core.java8.data.Student;

import java.util.stream.IntStream;

public class NumericStreamMapEx {

    public static void mapToObj() {
        IntStream.range(1, 6).mapToObj((i) -> i).forEach(System.out::println);
    }

    public static void mapToDouble() {
        IntStream.range(1, 6).mapToDouble((i) -> i).forEach(System.out::println);
    }

    public static void mapToLong() {
        IntStream.range(1, 6).mapToLong((i) -> i).forEach(System.out::println);
    }

    public static void main(String[] args) {

        mapToObj();
        mapToDouble();
        mapToLong();

    }
}
