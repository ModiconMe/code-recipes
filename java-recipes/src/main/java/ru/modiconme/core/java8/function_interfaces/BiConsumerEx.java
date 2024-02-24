package ru.modiconme.core.java8.function_interfaces;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.List;
import java.util.function.BiConsumer;

public class BiConsumerEx {

//    void accept(T t, U u);

    public static void printNameAndActivities() {
        BiConsumer<String, List<String>> biConsumer = (name, activities) -> System.out.println(name + " : " + activities);
        List<Student> studentList = StudentDataBase.getAllStudents();
        studentList.forEach((s) -> biConsumer.accept(s.getName(), s.getActivities()));
    }
    public static void main(String[] args) {

        BiConsumer<String, String> biConsumer = (a, b) -> System.out.println("a: " + a + "b: " + b);
        biConsumer.accept("java7", "java8");

        BiConsumer<Integer, Integer> multiply = (a, b) -> System.out.println("a * b = " + (a * b));

        BiConsumer<Integer, Integer> division = (a, b) -> System.out.println("a / b = " + (a / b));

        multiply.andThen(division).accept(10, 5);

        printNameAndActivities();

    }
}
