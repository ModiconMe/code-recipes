package ru.modiconme.core.java8.optional;

import java.util.Optional;

public class OptionalOfEmptyNullableEx {

    public static Optional<String> ofNullable() {
        return Optional.ofNullable("Hello");
//        return Optional.ofNullable(null); // Optional.empty()
    }

    public static Optional<String> of() {
        return Optional.of("Hello");
//        return Optional.of(null); // null pointer
    }

    public static Optional<String> empty() {
        return Optional.empty(); // no such el
    }

    public static void main(String[] args) {
        System.out.println(ofNullable().get());
        System.out.println(of().get());
//        System.out.println(empty().get());
    }
}
