package ru.modiconme.core.java8.parallel_stream;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class ParallelStreamEx {

    public static long checkPerformance(Supplier<?> supplier, int numberOfTimes) {
        long before = System.currentTimeMillis();
        for (int i = 0; i < numberOfTimes; i++) {
            supplier.get();
        }
        long after = System.currentTimeMillis();
        return after - before;
    }

    public static int sumSequentialStream() {
        return IntStream.rangeClosed(1, 100000000)
                .sum();
    }

    public static int sumParallelStream() {
        return IntStream.rangeClosed(1, 100000000)
                .parallel()
                .sum();
    }

    public static void main(String[] args) {

        System.out.println("SequentialStream");
        System.out.println(checkPerformance(ParallelStreamEx::sumSequentialStream, 120));
        System.out.println("ParallelStream");
        System.out.println(checkPerformance(ParallelStreamEx::sumParallelStream, 120));

    }
}
