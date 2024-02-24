package ru.modiconme.core.java8.streams_terminal;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.*;

public class StreamsMappingEx {

    public static void main(String[] args) {
        List<String> names1 = StudentDataBase.getAllStudents()
                .stream().collect(mapping(Student::getName, toList()));
        System.out.println("namesList:" + names1);

        Set<String> names2 = StudentDataBase.getAllStudents()
                .stream().collect(mapping(Student::getName, toSet()));
        System.out.println("namesList:" + names2);

    }
}
