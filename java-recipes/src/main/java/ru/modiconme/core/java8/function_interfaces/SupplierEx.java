package ru.modiconme.core.java8.function_interfaces;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class SupplierEx {

    public static void main(String[] args) {
        Supplier<Student> studentSupplier = () -> new Student("Adam", 2, 3.6, "male", 10, Optional.empty(), Arrays.asList("swimming", "basketball", "volleyball"));
        Supplier<List<Student>> listSupplier = StudentDataBase::getAllStudents;

        System.out.println(studentSupplier.get());
        System.out.println(listSupplier.get());
    }

}
