package ru.modiconme.core.java8.function_interfaces;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.List;
import java.util.function.Predicate;

public class PredicateStudentEx {

    static Predicate<Student> p1 = (s) -> s.getGradeLevel() >= 3;
    static Predicate<Student> p2 = (s) -> s.getGradeLevel() < 4;

    public static void filterStudentByGradeLevel() {
        List<Student> studentList = StudentDataBase.getAllStudents();
        studentList.stream().filter(p1.and(p2).negate()).forEach(System.out::println);
    }

    public static void main(String[] args) {
        filterStudentByGradeLevel();
    }
}
