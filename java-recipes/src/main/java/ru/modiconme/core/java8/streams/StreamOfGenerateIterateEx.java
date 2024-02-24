package ru.modiconme.core.java8.streams;

import java.util.Random;
import java.util.stream.Stream;

public class StreamOfGenerateIterateEx {

    public static void main(String[] args) {
        Stream<String> stream = Stream.of("adam", "julie", "dan");
        stream.forEach(System.out::println);

        Stream.iterate(1, x -> x * 2).limit(100).forEach(System.out::println);

        Stream.generate(new Random()::nextInt).limit(10).forEach(System.out::println);
    }
}
