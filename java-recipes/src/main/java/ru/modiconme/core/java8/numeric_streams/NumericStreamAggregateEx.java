package ru.modiconme.core.java8.numeric_streams;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class NumericStreamAggregateEx {

    public static void main(String[] args) {
        int sum = IntStream.rangeClosed(1, 50).sum();
        System.out.println("Sum = " + sum);

        OptionalInt max = IntStream.rangeClosed(1, 50).max();
        if (max.isPresent()) {
            System.out.println("Max = " + max.getAsInt());
        } else {
            System.out.println("Max is empty");
        }

        OptionalInt min = IntStream.rangeClosed(1, 50).min();
        if (min.isPresent()) {
            System.out.println("Min = " + min.getAsInt());
        } else {
            System.out.println("Min is empty");
        }

        OptionalDouble avg = IntStream.rangeClosed(1, 50).average();
        if (avg.isPresent()) {
            System.out.println("Avg = " + avg.getAsDouble());
        } else {
            System.out.println("Avg is empty");
        }



    }
}
