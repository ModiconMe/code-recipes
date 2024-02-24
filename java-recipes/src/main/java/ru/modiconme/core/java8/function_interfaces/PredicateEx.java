package ru.modiconme.core.java8.function_interfaces;

import java.util.function.Predicate;

public class PredicateEx {

//    boolean test(T t);

    static Predicate<Integer> predicate1 = i -> i%2 == 0;
    static Predicate<Integer> predicate2 = i -> i%5 == 0;

    public static void predicateAnd(int val) {
        System.out.println("PredicateAnd result : " + predicate1.and(predicate2).test(val)); // predicate chaining
    }

    public static void predicateOr(int val) {
        System.out.println("PredicateOr result : " + predicate1.or(predicate2).test(val)); // predicate chaining
    }

    public static void predicateNegate(int val) {
        System.out.println("PredicateOr with negate result : " + predicate1.or(predicate2).negate().test(val)); // predicate chaining
    }

    public static void main(String[] args) {
        System.out.println(predicate1.test(4));
        predicateAnd(10);
        predicateOr(9);
        predicateNegate(9);
    }
}
