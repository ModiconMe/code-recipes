package ru.modiconme.core.java8.streams_terminal;

import ru.modiconme.core.java8.data.StudentDataBase;

public class StreamsCountingEx {

    public static long count() {
        return StudentDataBase.getAllStudents().stream()
                .filter(student -> student.getGpa() >= 3.9)
                .count();
    }

    public static void main(String[] args) {
        System.out.println("Count: " + count());
    }
}
