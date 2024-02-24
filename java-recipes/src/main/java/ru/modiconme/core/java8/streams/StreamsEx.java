package ru.modiconme.core.java8.streams;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamsEx {

    public static void main(String[] args) {
        Map<String, List<String>> collect1 = StudentDataBase.getAllStudents().stream()
                .filter((s) -> s.getGradeLevel() >= 3)
                .filter((s) -> s.getGpa() > 3)
                .collect(Collectors.toMap(Student::getName, Student::getActivities));
        System.out.println(collect1);

        Map<String, List<String>> collect2 = StudentDataBase.getAllStudents().parallelStream()
                .peek(System.out::println) // debug streams
                .filter((s) -> s.getGradeLevel() >= 3)
                .peek(student -> System.out.println("After first filter:" + student)) // debug streams
                .filter((s) -> s.getGpa() > 3)
                .peek(student -> System.out.println("After second filter:" + student)) // debug streams
                .collect(Collectors.toMap(Student::getName, Student::getActivities));
        System.out.println(collect2);
    }
}
