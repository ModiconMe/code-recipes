package ru.modiconme.core.java8.function_interfaces;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BiFunctionEx {

    static BiFunction<List<Student>, Predicate<Student>, Map<String, Double>> function = (students, p) -> {
        Map<String, Double> mapOfStudents = new HashMap<>();
        List<Student> studentList = students.stream().filter(p).toList();
        studentList.forEach((s) -> mapOfStudents.put(s.getName(), s.getGpa()));
        return mapOfStudents;
    };

    static Predicate<Student> p = (s) -> s.getGpa() >= 2;

    public static void printStudents() {
        List<Student> allStudents = StudentDataBase.getAllStudents();
        System.out.println(function.apply(allStudents, p));

        Map<String, Double> collect = allStudents.stream()
                .filter(p)
                .collect(Collectors.toMap(Student::getName, Student::getGpa));
        System.out.println(collect);
    }
    public static void main(String[] args) {
        printStudents();
    }
}
