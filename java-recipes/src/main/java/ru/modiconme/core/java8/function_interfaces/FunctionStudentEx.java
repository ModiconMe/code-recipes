package ru.modiconme.core.java8.function_interfaces;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionStudentEx {

    static Function<List<Student>, Map<String, Double>> studentFunction = (students -> {
        Map<String, Double> studentGradeMap = new HashMap<>();
        students.forEach(student -> {
            studentGradeMap.put(student.getName(), student.getGpa());
        });

        return studentGradeMap;
    });

    public static void mapStudents() {
        List<Student> allStudents = StudentDataBase.getAllStudents();
        Map<String, Double> mapOfStudents1 = studentFunction.apply(allStudents);
        System.out.println(mapOfStudents1);

        Map<String, Double> mapOfStudents2 = StudentDataBase.getAllStudents().stream().collect(Collectors.toMap(Student::getName, Student::getGpa));
        System.out.println("Collect: " + mapOfStudents2);
    }
    public static void main(String[] args) {
        mapStudents();
    }
}
