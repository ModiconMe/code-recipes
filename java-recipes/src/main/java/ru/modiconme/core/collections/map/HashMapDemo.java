package ru.modiconme.core.collections.map;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// not synchronized
// key == null, value == null
public class HashMapDemo {
    public static void main(String[] args) {
        Map<Integer, String> map1 = new HashMap<>();

        map1.put(1000, "John Smith");
        map1.put(2000, "James Bond");
        map1.put(3000, "Jamila Ahmed");
        map1.putIfAbsent(3000, "Jamila Ahmed1");
        System.out.println(map1);

        Map<Person, String> map2 = new HashMap<>(16, 0.75f); // capacity = 16, size = 12 -> resize();
        Person person1 = new Person("John", "Smith", 30, Instant.now());
        Person person2 = new Person("James", "Bond", 45, Instant.now());
        map2.put(person1, "Smart");
        map2.put(person2, "Brave");
        System.out.println("containsKey(): " + map2.containsKey(person1)); // true O(1) -> O(n) or O(1) -> O(log(n)) (since Java 8)
        map2.containsValue("Smart"); // O(n)
        person1.setAge(31); // change key
        System.out.println("containsKey(): " + map2.containsKey(person1)); // false -> hash in the Node stay the same, but in our object its change
    }
}

class Person {
    private String firstName;
    private String lastName;
    private int age;
    private Instant dateOfBirth;

    public Person() {
    }

    public Person(String firstName, String lastName, int age, Instant dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(dateOfBirth, person.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, dateOfBirth);
    }
}
