package ru.modiconme.core.java8.numeric_streams;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class NumericStreamRangesEx {

    public static void main(String[] args) {

        IntStream intStream1 = IntStream.range(1, 50);
        System.out.println(intStream1.count()); // 49

        IntStream intStream2 = IntStream.rangeClosed(1, 50);
        System.out.println(intStream2.count()); // 50

        LongStream longStream1 = LongStream.range(1L, 50L);
        System.out.println(longStream1.count()); // 49

        DoubleStream doubleStream = IntStream.range(1, 50).asDoubleStream();
        System.out.println(doubleStream.count());
    }
}
