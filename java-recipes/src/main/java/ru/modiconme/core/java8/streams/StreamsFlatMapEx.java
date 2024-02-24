package ru.modiconme.core.java8.streams;

import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.Comparator;
import java.util.List;

public class StreamsFlatMapEx {

    public static List<String> printStudentsActivities() {
        return StudentDataBase.getAllStudents().stream()
                .flatMap((s) -> s.getActivities().stream())
                .distinct()
                .toList();
    }

    public static List<String> printStudentsActivitiesSorted() {
        return StudentDataBase.getAllStudents().stream()
                .flatMap((s) -> s.getActivities().stream())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    public static Long printStudentsActivitiesCount() {
        return StudentDataBase.getAllStudents().stream()
                .flatMap((s) -> s.getActivities().stream())
                .distinct()
                .count();
    }

    public static void main(String[] args) {
        System.out.println(printStudentsActivities());
        System.out.println(printStudentsActivitiesSorted());
        System.out.println(printStudentsActivitiesCount());
    }
}
