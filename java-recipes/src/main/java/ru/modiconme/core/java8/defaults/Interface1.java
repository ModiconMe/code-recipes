package ru.modiconme.core.java8.defaults;

public interface Interface1 {

    default void methodA() {
        System.out.println("Inside methodA" + Interface1.class);
    }
}
