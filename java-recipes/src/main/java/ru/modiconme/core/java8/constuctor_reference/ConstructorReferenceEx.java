package ru.modiconme.core.java8.constuctor_reference;

import ru.modiconme.core.java8.data.Student;

import java.util.function.Function;
import java.util.function.Supplier;

public class ConstructorReferenceEx {

//    static Supplier<Student> studentSupplier = Student::new; // use Student() constructor
//
//    static Function<String, Student> studentFunction = Student::new; // use Student(String name) constructor
//
//    public static void main(String[] args) {
//        System.out.println(studentSupplier.get());
//        System.out.println(studentFunction.apply("name"));
//    }
}
