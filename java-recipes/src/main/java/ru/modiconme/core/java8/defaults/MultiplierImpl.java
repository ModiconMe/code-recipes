package ru.modiconme.core.java8.defaults;

import java.util.List;

public class MultiplierImpl implements Multiplier {

    @Override
    public int multiply(List<Integer> integers) {
        return integers.stream().reduce(1, (x, y) -> x * y);
    }

    @Override
    public int size(List<Integer> integers) {
        System.out.println("Inside multiplier class");
        return integers.size();
    }

}
