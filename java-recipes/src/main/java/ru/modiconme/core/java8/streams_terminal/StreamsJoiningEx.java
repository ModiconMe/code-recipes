package ru.modiconme.core.java8.streams_terminal;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.stream.Collectors;

public class StreamsJoiningEx {

    public static String joining1() {
        return StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.joining());
    }

    public static String joining2() {
        return StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.joining(", "));
    }

    public static String joining3() {
        return StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .collect(Collectors.joining(", ", "**", "**"));
    }

    public static void main(String[] args) {
        System.out.println("Joining1: " + joining1());
        System.out.println("Joining2: " + joining2());
        System.out.println("Joining3: " + joining3());
    }
}
