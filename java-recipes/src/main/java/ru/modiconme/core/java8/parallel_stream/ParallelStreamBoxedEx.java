package ru.modiconme.core.java8.parallel_stream;

import java.util.List;
import java.util.stream.IntStream;

public class ParallelStreamBoxedEx {

    public static long sequentialSum(List<Integer> integers) {
        long startTime = System.currentTimeMillis();
        integers.stream()
                .reduce(0, Integer::sum);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static long parallelSum(List<Integer> integers) {
        long startTime = System.currentTimeMillis();
        integers.parallelStream()
                .reduce(0, Integer::sum);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static void main(String[] args) {
        List<Integer> integers = IntStream.rangeClosed(1, 10000000).boxed().toList();

        System.out.println("Seq: " + sequentialSum(integers));
        System.out.println("Parallel: " + parallelSum(integers));

    }
}
