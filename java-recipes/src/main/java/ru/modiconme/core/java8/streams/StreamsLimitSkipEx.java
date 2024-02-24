package ru.modiconme.core.java8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamsLimitSkipEx {

    public static Optional<Integer> limitInt(List<Integer> integers) {
        return integers.stream()
                .limit(2) // 6, 7
                .reduce(Integer::sum);
    }

    public static Optional<Integer> skipInt(List<Integer> integers) {
        return integers.stream()
                .skip(2)
                .limit(2) // 8, 9
                .reduce(Integer::sum);
    }

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(6,7,8,9,10);
        System.out.println(limitInt(integers).get());
        System.out.println(skipInt(integers).get());
    }
}
