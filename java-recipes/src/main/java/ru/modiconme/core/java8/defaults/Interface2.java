package ru.modiconme.core.java8.defaults;

public interface Interface2 extends Interface1 {

    default void methodB() {
        System.out.println("Inside methodB");
    }

    default void methodA() {
        System.out.println("Inside methodA" + Interface2.class);
    }
}
