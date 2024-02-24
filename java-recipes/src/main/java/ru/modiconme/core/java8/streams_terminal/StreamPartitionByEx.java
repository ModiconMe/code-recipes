package ru.modiconme.core.java8.streams_terminal;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toSet;

public class StreamPartitionByEx {

    public static void partitioningBy1() {
        Predicate<Student> studentPredicate = s -> s.getGpa() >= 3.9;
        Map<Boolean, List<Student>> collect = StudentDataBase.getAllStudents()
                .stream()
                .collect(partitioningBy(studentPredicate));
        System.out.println(collect);
    }

    public static void partitioningBy2() {
        Predicate<Student> studentPredicate = s -> s.getGpa() >= 3.9;
        Map<Boolean, Set<Student>> collect = StudentDataBase.getAllStudents()
                .stream()
                .collect(
                        partitioningBy(studentPredicate,
                                toSet()
                        ));
        System.out.println(collect);
    }

    public static void main(String[] args) {
        partitioningBy1();
        partitioningBy2();
    }
}
