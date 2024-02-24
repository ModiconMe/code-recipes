package ru.modiconme.core.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionEx1 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class<Employee> employeeClass = Employee.class;
//        Class<?> employeeClass = new Employee().getClass();
//        Class<?> employeeClass = Class.forName("reflection.ReflectionEx1");
        Field field = employeeClass.getField("id");

        Constructor<Employee> constructor = employeeClass.getConstructor();
//        Employee employee = (Employee) constructor.newInstance();
        Employee employee = constructor.newInstance();
        employee.setSalary(100.0);
        System.out.println("Type of id field: " + field.getType());
        System.out.println("Value of id field: " + field.get(employee));
        System.out.println("------------------------");

        field.set(employee, 123);
        System.out.println(employee);
        System.out.println("------------------------");

        Field[] fields = employeeClass.getFields(); // except private
        Arrays.stream(fields).forEach(System.out::println);
        System.out.println("------------------------");

        Field[] declaredFields = employeeClass.getDeclaredFields(); // include private
        Arrays.stream(declaredFields).forEach(System.out::println);
        System.out.println("------------------------");

        Method method = employeeClass.getDeclaredMethod("increaseSalary");
        System.out.println("Return type of method: " + method.getReturnType());
        System.out.println("Name of method: " + method.getName());
        System.out.println("Annotations of method: " + Arrays.toString(method.getAnnotations()));

        method.setAccessible(true); // set access to private method
        method.invoke(employee); // invoke increaseSalary() method
        System.out.println(employee);
        System.out.println("------------------------");
    }
}

class Employee {
    public int id;
    public String name;
    public String department;
    private double salary;

    public Employee() {
    }

    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    private void changeDepartment(String department) {
        this.department = department;
        System.out.println("New department is " + department);
    }

    @Deprecated
    private void increaseSalary() {
        this.salary *= 2;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}
