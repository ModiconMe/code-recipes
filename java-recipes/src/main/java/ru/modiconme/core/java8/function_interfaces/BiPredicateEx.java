package ru.modiconme.core.java8.function_interfaces;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.List;
import java.util.function.BiPredicate;

public class BiPredicateEx {

//    boolean test(T t, U u);

    static BiPredicate<Integer, Double> biPredicate = (gradeLevel, gpa) -> gradeLevel >= 3 && gpa >= 3;

    public static void main(String[] args) {
        List<Student> allStudents = StudentDataBase.getAllStudents();
//        allStudents.forEach((s) -> {
//            if (biPredicate.test(s.getGradeLevel(), s.getGpa())) {
//                System.out.println(s);
//            }
//        });
        allStudents.stream()
                .filter((s) -> biPredicate.test(s.getGradeLevel(), s.getGpa()))
                .forEach(System.out::println);
    }
}
