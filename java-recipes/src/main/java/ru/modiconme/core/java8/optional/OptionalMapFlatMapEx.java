package ru.modiconme.core.java8.optional;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.Optional;

public class OptionalMapFlatMapEx {

    // filter
    public static void optionalFilter() {
        Optional<Student> student = Optional.ofNullable(StudentDataBase.studentSupplier.get());

        student.filter(student1 -> student1.getGpa() >= 3.5)
                .ifPresent(System.out::println);
    }

    // map
    public static void optionalMap() {
        Optional<Student> student = Optional.ofNullable(StudentDataBase.studentSupplier.get());

        if (student.isPresent()) {
            student.map(Student::getName).ifPresent(System.out::println);
        }
    }

    // flatmap
    public static void optionalFlatmap() {
        Optional<Student> student = Optional.ofNullable(StudentDataBase.getOptionalStudent().get());

        student.filter(student1 -> student1.getGpa() >= 3.6)
                .flatMap(Student::getBike) // Optional<Bike>
                .ifPresent(System.out::println);
    }

    public static void main(String[] args) {
        optionalFilter();
        optionalMap();
        optionalFlatmap();
    }
}
