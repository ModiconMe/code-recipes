package ru.modiconme.core.java8.streams;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

public class StreamsReduceEx {

    static List<Integer> integers = Arrays.asList(1,2,5,6);

    public static int performMultiplication() {
        // a = 1, b = 1 (from stream) => result = 1 -> a = 1 (result), b = 2 (from stream) => result = 2 -> a = 2 (result), b = 5 ...
        return integers.stream().reduce(1, (a, b) -> a * b); // if list is empty return 1
    }

    public static Optional<Integer> performMultiplicationWithOutIdentity() {
        // a = 1, b = 1 (from stream) => result = 1 -> a = 1 (result), b = 2 (from stream) => result = 2 -> a = 2 (result), b = 5 ...
        return integers.stream().reduce((a, b) -> a * b); // if list is empty return optional of null
    }

    public static Optional<Student> getStudentWithHighestGpa() {
        return StudentDataBase.getAllStudents().stream()
                .reduce((s1, s2) -> BinaryOperator.maxBy(Comparator.comparing(Student::getGpa)).apply(s1, s2));
//                .reduce((s1, s2) -> s1.getGpa() > s2.getGpa() ? s1 : s2);
    }

    public static void main(String[] args) {
        System.out.println(performMultiplication());
        System.out.println(performMultiplicationWithOutIdentity().get());
        System.out.println(getStudentWithHighestGpa().get());
    }
}
