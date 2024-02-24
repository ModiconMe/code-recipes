package ru.modiconme.core.java8.streams;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.List;

public class StreamsMapEx {

    public static void main(String[] args) {
        List<String> names = StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .toList();
        System.out.println(names);
    }
}
