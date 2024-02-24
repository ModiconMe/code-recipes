package ru.modiconme.core.java8.numeric_streams;

import java.util.List;
import java.util.stream.IntStream;

public class NumericStreamsBoxingUnBoxingEx {

    public static List<Integer> boxing() {
        return IntStream.rangeClosed(1, 10).boxed().toList();
    }

    public static void unboxing(List<Integer> integers) {
        integers.stream().mapToInt(Integer::valueOf).forEach(System.out::println);
    }

    public static void main(String[] args) {

        System.out.println("Boxing: " + boxing());

        unboxing(List.of(1,2,3,4,5));

    }
}
