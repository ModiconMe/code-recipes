package ru.modiconme.core.java8.defaults;

public interface Interface4 {

    default void methodA() {
        System.out.println("Inside methodA" + Interface4.class);
    }
}
