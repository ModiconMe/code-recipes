package ru.modiconme.core.java8.streams;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.Comparator;
import java.util.List;

public class StreamsFilterEx {

    public static List<Student> filterStudents() {
        return StudentDataBase.getAllStudents().stream()
                .filter(s -> s.getGpa() >= 3.8)
                .sorted(Comparator.comparing(Student::getGpa))
                .toList();
    }

    public static void main(String[] args) {
        filterStudents().forEach(System.out::println);
    }
}
