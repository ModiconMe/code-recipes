package ru.modiconme.core.java8.function_interfaces;

import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.util.List;
import java.util.function.Consumer;

public class ConsumerEx {

//    void accept(T t);

    static Consumer<Student> c2 = System.out::println;
    static Consumer<Student> c3 = (s) -> System.out.print(s.getName() + ", grade: " + s.getGradeLevel());
    static Consumer<Student> c4 = (s) -> System.out.println(s.getActivities());

    public static void printName() {

        List<Student> students = StudentDataBase.getAllStudents();
        students.forEach(c2);
    }

    public static void printNameAndActivities() {
        List<Student> students = StudentDataBase.getAllStudents();

        students.forEach(c3.andThen(c4)); // consumer chain
    }

    public static void printNameAndActivitiesFiltered(int grade) {
        List<Student> students = StudentDataBase.getAllStudents();

        students.stream().filter(student -> student.getGradeLevel() > grade).forEach(c3.andThen(c4)); // consumer chain
    }

    public static void main(String[] args) {

        Consumer<String> consumer1 = (s) -> System.out.println(s.toUpperCase());
        consumer1.accept("hello");

        System.out.println("------------");
        printName();
        System.out.println("------------");
        printNameAndActivities();
        System.out.println("------------");
        printNameAndActivitiesFiltered(2);
    }
}
