package ru.modiconme.core.java8.numeric_streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class NumericStreamsEx {


    public static int sumOfNumbers(List<Integer> integerList) {
        return integerList.stream().reduce(0, Integer::sum);
    }

    public static int sumOfNumbersNumeric(List<Integer> integerList) {
        return integerList.stream().mapToInt(Integer::valueOf).sum();
    }

    public static int sumOfNumbersNumeric2(int start, int end) {
        return IntStream.rangeClosed(start, end).sum();
    }

    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        System.out.println("Sum:" + sumOfNumbers(integerList));
        System.out.println("Sum:" + sumOfNumbersNumeric(integerList));
        System.out.println("Sum:" + sumOfNumbersNumeric2(1, 7));
    }
}
