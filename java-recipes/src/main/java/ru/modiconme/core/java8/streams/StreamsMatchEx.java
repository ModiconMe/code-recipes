package ru.modiconme.core.java8.streams;

import ru.modiconme.core.java8.data.StudentDataBase;

public class StreamsMatchEx {

    public static boolean allMatch() {
        return StudentDataBase.getAllStudents().stream()
                .allMatch(student -> student.getGpa() >= 4.0); // return true if all elements in stream match the predicate
    }

    public static boolean anyMatch() {
        return StudentDataBase.getAllStudents().stream()
                .anyMatch(student -> student.getGpa() >= 4.0); // return true if any elements in stream match the predicate
    }

    public static boolean noneMatch() {
        return StudentDataBase.getAllStudents().stream()
                .noneMatch(student -> student.getGpa() >= 4.0); // return true if no elements in stream match the predicate
    }

    public static void main(String[] args) {
        System.out.println("All match " + allMatch());
        System.out.println("Any match " + anyMatch());
        System.out.println("None match " + noneMatch());
    }
}
