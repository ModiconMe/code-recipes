package ru.modiconme.core.collections;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionsDemo {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(-3, 8, 13, -5, 41, 44, 50, -90);
        Collections.sort(list);
        System.out.println(list);
        int i = Collections.binarySearch(list, 8);
        System.out.println(i);

        Person person1 = new Person("John", 38, Instant.now());
        Person person2 = new Person("Jim", 12, Instant.now());
        Person person3 = new Person("James", 90, Instant.now());

        List<Person> people = Arrays.asList(person1, person2, person3);
        Collections.sort(people);
        Collections.reverse(list);
        Collections.shuffle(list);
//        people.sort(Comparator.comparing(Person::getDateOfBirth));
        System.out.println(people);
    }
}

class Person implements Comparable<Person> {
    private String name;
    private int age;
    private Instant dateOfBirth;

    public Person(String name, int age, Instant dateOfBirth) {
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return this.age - o.age;
    }
}
