package ru.modiconme.core.java8.parallel_stream;

import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.List;

public class ParallelStreamEx1 {

    public static long sequentialPrintStudentsActivities() {
        long startTime = System.currentTimeMillis();
        List<String> strings = StudentDataBase.getAllStudents().stream()
                .flatMap((s) -> s.getActivities().stream())
                .distinct()
                .toList();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static long parallelPrintStudentsActivities() {
        long startTime = System.currentTimeMillis();
        List<String> strings = StudentDataBase.getAllStudents().parallelStream()
                .flatMap((s) -> s.getActivities().stream())
                .distinct()
                .toList();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static void main(String[] args) {
        System.out.println("Seq:" + sequentialPrintStudentsActivities());
        System.out.println("Parallel:" + parallelPrintStudentsActivities());
    }
}
