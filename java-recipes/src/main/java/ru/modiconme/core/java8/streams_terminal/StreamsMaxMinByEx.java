package ru.modiconme.core.java8.streams_terminal;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.Comparator;
import java.util.Optional;

import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;

public class StreamsMaxMinByEx {

    public static Optional<Student> minByEx() {
        return StudentDataBase.getAllStudents()
                .stream()
//                .min(Comparator.comparing(Student::getGpa));
                .collect(minBy(Comparator.comparing(Student::getGpa)));
    }

    public static Optional<Student> maxByEx() {
        return StudentDataBase.getAllStudents()
                .stream()
//                .max(Comparator.comparing(Student::getGpa));
                .collect(maxBy(Comparator.comparing(Student::getGpa)));
    }

    public static void main(String[] args) {
        System.out.println(minByEx());
        System.out.println(maxByEx());
    }
}
