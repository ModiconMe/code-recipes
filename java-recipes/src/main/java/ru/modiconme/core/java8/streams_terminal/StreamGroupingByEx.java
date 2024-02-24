package ru.modiconme.core.java8.streams_terminal;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.*;

import static java.util.stream.Collectors.*;

public class StreamGroupingByEx {

    public static void groupStudentsByGender() {
        Map<String, List<Student>> collect = StudentDataBase.getAllStudents()
                .stream()
                .collect(groupingBy(Student::getGender));
        System.out.println("Group by gender: " + collect);
    }

    public static void groupStudentsByCustomize() {
        Map<String, List<Student>> collect = StudentDataBase.getAllStudents()
                .stream()
                .collect(groupingBy(student -> student.getGpa() >= 3.8 ? "OUTSTANDING" : "AVERAGE"));
        System.out.println("Group by custom values by gpa: " + collect);
    }

    public static void twoLevelGrouping1() {
        Map<Integer, Map<String, List<Student>>> collect = StudentDataBase.getAllStudents()
                .stream()
                .collect(
                        groupingBy(
                                Student::getGradeLevel,
                                groupingBy(student -> student.getGpa() >= 3.8 ? "OUTSTANDING" : "AVERAGE")
                        )
                );
        System.out.println("Group by gender and gpa: " + collect);
    }

    public static void twoLevelGrouping2() {
        Map<Integer, Integer> collect = StudentDataBase.getAllStudents()
                .stream()
                .collect(
                        groupingBy(
                                Student::getGradeLevel, // group students by grade level
                                summingInt(Student::getNoteBooks) // sum number of notebook in each group
                        )
                );
        System.out.println("Group by gender and sum notebook: " + collect);
    }

    public static void thereArgGroupBy() {
        LinkedHashMap<String, Set<Student>> collect = StudentDataBase.getAllStudents()
                .stream()
                .collect(
                        groupingBy(
                                Student::getName,
                                LinkedHashMap::new,
                                toSet()
                        )
                );
        System.out.println("Group by name collect to set and linkedhashmap: " + collect);
    }

    public static void calculateTopGpa() {
        Map<Integer, Student> collect = StudentDataBase.getAllStudents()
                .stream()
                .collect(groupingBy(
                        Student::getGradeLevel, // group by grade level
                        collectingAndThen(maxBy(
                                Comparator.comparing(Student::getGpa)),
                                Optional::get
                        ) // each group includes only biggest gpa that exist in that group
                ));
        System.out.println("Group by grade level max by gpa: " + collect);
    }

    public static void calculateBottomGpa() {
        Map<Integer, Student> collect = StudentDataBase.getAllStudents()
                .stream()
                .collect(groupingBy(
                        Student::getGradeLevel, // group by grade level
                        collectingAndThen(minBy(
                                        Comparator.comparing(Student::getGpa)),
                                Optional::get
                        ) // each group includes only biggest gpa that exist in that group
                ));
        System.out.println("Group by grade level min by gpa: " + collect);
    }

    public static void main(String[] args) {
        groupStudentsByGender();
        groupStudentsByCustomize();

        twoLevelGrouping1();
        twoLevelGrouping2();

        thereArgGroupBy();

        calculateTopGpa();
        calculateBottomGpa();
    }
}
