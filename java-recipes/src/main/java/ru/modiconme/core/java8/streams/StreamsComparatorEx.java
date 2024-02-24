package ru.modiconme.core.java8.streams;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.Comparator;
import java.util.List;

public class StreamsComparatorEx {

    public static List<Student> sortStudentsByName() {
        return StudentDataBase.getAllStudents()
                .stream()
                .sorted(Comparator.comparing(Student::getName))
                .toList();
    }

    public static List<Student> sortStudentsByGpa() {
        return StudentDataBase.getAllStudents()
                .stream()
                .sorted(Comparator.comparing(Student::getGpa))
                .toList();
    }

    public static List<Student> sortStudentsByGpaDesc() {
        return StudentDataBase.getAllStudents()
                .stream()
                .sorted(Comparator.comparing(Student::getGpa).reversed())
                .toList();
    }

    public static void main(String[] args) {
        System.out.println("Sort students by name");
        sortStudentsByName().forEach(System.out::println);
        System.out.println("Sort students by gpa");
        sortStudentsByGpa().forEach(System.out::println);
        System.out.println("Sort desc students by gpa");
        sortStudentsByGpaDesc().forEach(System.out::println);
    }
}
