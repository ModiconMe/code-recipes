package ru.modiconme.core.java8.lambdas;

import java.util.Comparator;

public class ComparatorLambdaEx {

    public static void main(String[] args) {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };

        // lambda #1
        Comparator<Integer> comparatorLambda1 = (o1, o2) -> o1.compareTo(o2);

        // lambda #2
        Comparator<Integer> comparatorLambda2 = Integer::compareTo;

    }
}
