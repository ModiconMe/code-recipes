package ru.modiconme.core.java8.optional;

import java.util.Optional;

public class OptionalPresentEx {

    public static void main(String[] args) {

        Optional<String> optional = Optional.ofNullable("hello");

        // isPresent
        System.out.println(optional.isPresent());
        if (optional.isPresent()) {
            System.out.println(optional.get());
        }

        // ifPresent
        optional.ifPresent(System.out::println);

    }
}
