package ru.modiconme.core.java8.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class StudentDataBase {

    public static Supplier<Student> studentSupplier = () -> new Student("Adam", 2, 4.0, "male", 1, Optional.empty(), List.of("swimming", "basketball", "volleyball"));

    public static Optional<Student> getOptionalStudent() {
        Bike bike = new Bike("Client123", "Client123");
        Student student = new Student("Adam", 2, 4.0, "male", 1, Optional.of(bike), List.of("swimming", "basketball", "volleyball"));
        return Optional.of(student);
    }

    /**
     * Total of 6 students in the database.
     *
     * @return
     */
    public static List<Student> getAllStudents() {

        /**
         * 2nd grade students
         */
        Bike bike1 = new Bike("bike1", "LM123");
        Bike bike2 = new Bike("bike2", "LM321");

        Student student1 = new Student("Adam", 2, 3.6, "male", 10, Optional.of(bike1), List.of("swimming", "basketball", "volleyball"));
        Student student2 = new Student("Jenny", 2, 3.8, "female", 11, Optional.empty(), List.of("swimming", "gymnastics", "soccer"));
        /**
         * 3rd grade students
         */
        Student student3 = new Student("Emily", 3, 4.0, "female", 12, Optional.of(bike2), List.of("swimming", "gymnastics", "aerobics"));
        Student student4 = new Student("Dave", 3, 4.0, "male", 15, Optional.empty(), List.of("swimming", "gymnastics", "soccer"));
        /**
         * 4th grade students
         */
        Student student5 = new Student("Sophia", 4, 3.5, "female", 10, Optional.empty(), List.of("swimming", "dancing", "football"));
        Student student6 = new Student("James", 4, 3.9, "male", 22, Optional.empty(), List.of("swimming", "basketball", "baseball", "football"));

        return List.of(student1, student2, student3, student4, student5, student6);
    }
}
