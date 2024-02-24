package ru.modiconme.core.io.serialization;

import java.io.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class ObjectIOStreams {
    static File file = new File("src/main/resources/employee.bin");

    public static void main(String[] args) {

        try (
//                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))
        ) {
//            writeToFile(oos);
            readFromFile(ois);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToFile(ObjectOutputStream oos) {
        Employee employee1 = new Employee(
                "John",
                "Doe",
                Instant.now(),
                Arrays.asList("Monday", "Wednesday")
        );
        Employee employee2 = new Employee(
                "James",
                "Bond",
                Instant.now(),
                Arrays.asList("Saturday", "Sunday")
        );
        try {
            oos.writeObject(employee1);
            oos.writeObject(employee2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFromFile(ObjectInputStream ois) {
        try {
            Employee employee;
            while ((employee = (Employee) ois.readObject()) != null) // better write several objects in ArrayList
                System.out.println(employee);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

class Employee implements Serializable {
    static final long serialVersionUID = 2L;
    private String firstName;
    private String lastName;
    private Instant dateOfBirth;
    private List<String> workDays;

    public Employee(String firstName, String lastName, Instant dateOfBirth, List<String> workDays) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.workDays = workDays;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<String> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(List<String> workDays) {
        this.workDays = workDays;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", workDays=" + workDays +
                '}';
    }
}
