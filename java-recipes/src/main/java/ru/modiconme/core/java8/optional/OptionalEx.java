package ru.modiconme.core.java8.optional;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.Optional;

public class OptionalEx {

    public static String getStudentName() {
        Student student = StudentDataBase.studentSupplier.get();

        if (student != null) {
            return student.getName();
        }

        return null;
    }

    public static Optional<String> getStudentNameOptional() {
        Optional<Student> student = Optional.ofNullable(StudentDataBase.studentSupplier.get());

        if (student.isPresent()) {
            return student.map(Student::getName);
        }

        return Optional.empty();
    }

    public static void main(String[] args) {
        String name = getStudentName();
        if (name != null) {
            System.out.println("Length: " + name.length());
        } else {
            System.out.println("Name not found");
        }

        Optional<String> studentNameOptional = getStudentNameOptional();
        if (studentNameOptional.isPresent()) {
            System.out.println("Length: " + studentNameOptional.get().length());
        } else {
            System.out.println("Name not found");
        }
    }
}
