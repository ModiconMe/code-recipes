package ru.modiconme.core.java8.defaults;

import java.util.List;

public interface Multiplier {

    int multiply(List<Integer> integers);

    default int size(List<Integer> integers) {
        System.out.println("Inside multiplier interface");
        return integers.size();
    }

    static boolean isEmpty(List<Integer> integers) {
        return integers.isEmpty();
    }
}
