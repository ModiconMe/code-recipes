package ru.modiconme.core.java8.method_reference;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.function.Consumer;

public class ConsumerMethodReferenceEx {

    static Consumer<Student> c1 = System.out::println;

    public static void main(String[] args) {
        StudentDataBase.getAllStudents().forEach(c1);
    }
}
