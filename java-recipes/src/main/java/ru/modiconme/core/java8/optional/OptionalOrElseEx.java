package ru.modiconme.core.java8.optional;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.Optional;

public class OptionalOrElseEx {

    public static String optionalOrElse() {
        return Optional.ofNullable(StudentDataBase.studentSupplier.get())
                .map(Student::getName)
                .orElse("Student name is empty");
    }

    public static String optionalOrElseGet() {
        return Optional.ofNullable(StudentDataBase.studentSupplier.get())
                .map(Student::getName)
                .orElseGet(() -> StudentDataBase.studentSupplier.get().toString()); // return default value
    }

    public static String optionalOrElseThrow() {
        return Optional.ofNullable(StudentDataBase.studentSupplier.get())
                .map(Student::getName)
                .orElseThrow(IllegalArgumentException::new);
    }

    public static void main(String[] args) {

        System.out.println(optionalOrElse());
        System.out.println(optionalOrElseGet());
        System.out.println(optionalOrElseThrow());

    }
}
