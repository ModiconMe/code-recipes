package ru.modiconme.core.java8.streams_terminal;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.summingInt;

public class StreamsSumIntAvgIntEx {

    public static int sum() {
        return StudentDataBase.getAllStudents()
                .stream()
                .collect(summingInt(Student::getNoteBooks));
//                .mapToInt(Student::getNoteBooks).sum();
    }

    public static double avg() {
        return StudentDataBase.getAllStudents()
                .stream()
                .collect(averagingInt(Student::getNoteBooks));
//                .mapToDouble(Student::getNoteBooks).average().getAsDouble();
    }

    public static void main(String[] args) {
        System.out.println("Sum: " + sum());
        System.out.println("Avg: " + avg());
    }
}
