package ru.modiconme.core.java8.streams;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.Optional;

public class StreamsFindAnyFirstEx {

    public static Optional<Student> findAnyStudent() {
        return StudentDataBase.getAllStudents().stream()
                .filter(student -> student.getGpa() >= 3.9)
                .findAny();
    }

    public static Optional<Student> findAnyStudentParallel() {
        return StudentDataBase.getAllStudents().parallelStream()
                .filter(student -> student.getGpa() >= 3.9)
                .findAny();
    }

    public static Optional<Student> findFirstStudent() {
        return StudentDataBase.getAllStudents().stream()
                .filter(student -> student.getGpa() >= 3.9)
                .findFirst();
    }

    public static Optional<Student> findFirstStudentParallel() {
        return StudentDataBase.getAllStudents().parallelStream()
                .filter(student -> student.getGpa() >= 3.9)
                .findFirst();
    }

    public static void main(String[] args) {

        System.out.println(findAnyStudent());
        System.out.println(findAnyStudentParallel());
        System.out.println(findFirstStudent());
        System.out.println(findFirstStudentParallel());

    }
}
