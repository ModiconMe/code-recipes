package ru.modiconme.core.java8.streams;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

public class StreamsMapReduceEx {

    private static Integer noOfNoteBooks() {
        return StudentDataBase.getAllStudents().stream()
                .filter(s -> s.getGradeLevel() >= 3)
                .map(Student::getNoteBooks)
//                .reduce(0, (a, b) -> a + b);
                .reduce(0, Integer::sum);

//        return StudentDataBase.getAllStudents().stream()
//                .filter(s -> s.getGradeLevel() >= 3)
//                .mapToInt(Student::getNoteBooks)
//                .sum();
    }

    public static void main(String[] args) {
        System.out.println(noOfNoteBooks());
    }
}
