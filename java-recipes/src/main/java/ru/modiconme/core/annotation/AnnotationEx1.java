package ru.modiconme.core.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class AnnotationEx1 {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        Employee instance = createInstance(Employee.class);
        System.out.println(instance);
    }

    public static Employee createInstance(Class<Employee> clazz) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(CreateInstance.class)) {
                    Employee instance = (Employee) method.invoke(null);

                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        Annotation[] fieldAnnotations = field.getAnnotations();
                        for (Annotation annotation1 : fieldAnnotations) {
                            if (annotation1.annotationType().equals(RandomValue.class)) {
                                field.setInt(instance, new Random().nextInt(1, 100));
                            }
                        }
                    }

                    return instance;
                }
            }
        }
        throw new IllegalStateException("factory method not exists");
    }

}

class Employee {
    public double salary;

    @MyAnnotation(value = "Bob")
    public String name;

    @RandomValue
    public int age;

    public Employee() {
    }

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @CreateInstance
    public static Employee newInstance() {
        return new Employee();
    }

    public void increaseSalary() {
        salary *= 2;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

//@Target(ElementType.TYPE) // class
//@Target(ElementType.FIELD) // field
//@Target(ElementType.METHOD) // method
//@Target(ElementType.PARAMETER) // parameter
//@Target({ElementType.FIELD, ElementType.METHOD})

// source -> class -> runtime
//@Retention(RetentionPolicy.CLASS) // byte code (after compiling)
//@Retention(RetentionPolicy.RUNTIME) // runtime
//@Retention(RetentionPolicy.SOURCE) // source code (use when compiling)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String value();
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface RandomValue {

}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface CreateInstance {

}
