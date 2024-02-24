package ru.modiconme.core.java8.streams;

import java.util.*;

public class StreamsMinMaxEx {

    public static int maxValue(List<Integer> integerList) {
        // 6 -> y
        // 7 -> y
        // 8 -> y
        // 9 -> y
        // 10 -> y
        // x variable holds the max value for each element in the iteration
        return integerList.stream().reduce(0, (x, y) -> x > y ? x : y);
    }

    public static Optional<Integer> minValue(List<Integer> integerList) {
        // 6 -> y
        // 7 -> y
        // 8 -> y
        // 9 -> y
        // 10 -> y
        // x variable holds the max value for each element in the iteration
        return integerList.stream().reduce ((x, y) -> x < y ? x : y);
    }

    public static void main(String[] args) {
        List<Integer> integerList1 = Arrays.asList(6,7,8,9,10);
        List<Integer> integerList2 = new ArrayList<>();

        System.out.println(maxValue(integerList1)); // return 10
        System.out.println(maxValue(integerList2)); // 0
        System.out.println(minValue(integerList1).get()); // 6
        System.out.println(minValue(integerList2).isPresent()); // false
    }
}
