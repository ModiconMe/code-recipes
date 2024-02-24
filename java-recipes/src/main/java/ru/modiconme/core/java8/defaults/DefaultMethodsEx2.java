package ru.modiconme.core.java8.defaults;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class DefaultMethodsEx2 {

    static Consumer<Student> studentConsumer = System.out::println;

    public static void sortByName(List<Student> allStudents) {
        System.out.println("After name sort");
        Comparator<Student> nameComparator = Comparator.comparing(Student::getName);
        allStudents.sort(nameComparator);
        allStudents.forEach(studentConsumer);
    }

    public static void sortByNameAndGpa(List<Student> allStudents) {
        System.out.println("After name and gpa sort");
//        Comparator<Student> comparator = Comparator.comparing(Student::getName).thenComparingDouble(Student::getGpa);
        Comparator<Student> nameComparator = Comparator.comparing(Student::getName);
        Comparator<Student> gpaComparator = Comparator.comparing(Student::getGpa);
        allStudents.sort(nameComparator.thenComparing(gpaComparator)); // chaining
        allStudents.forEach(studentConsumer);
    }

    public static void sortWithNullValues(List<Student> students) {
        System.out.println("After sort with null values");
        Comparator<Student> nameComparator = Comparator.comparing(Student::getName);
        Comparator<Student> studentComparator = Comparator.nullsFirst(nameComparator); // if collection contain null values -> then null comes first
        students.sort(studentComparator);
        students.forEach(studentConsumer);
    }

    public static void main(String[] args) {
        List<Student> allStudents = StudentDataBase.getAllStudents();
        System.out.println("Before sort");
        allStudents.forEach(studentConsumer);

        sortByName(allStudents);
        sortByNameAndGpa(allStudents);
        sortWithNullValues(allStudents);
    }
}
