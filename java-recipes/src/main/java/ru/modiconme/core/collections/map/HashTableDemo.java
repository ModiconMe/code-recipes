package ru.modiconme.core.collections.map;

import java.time.Instant;
import java.util.Hashtable;

// synchronized
// key != null, value != null
// DEPRECATED -> user HashMap
public class HashTableDemo {
    public static void main(String[] args) {
        Hashtable<Double, Person> hashtable = new Hashtable<>();
        Person person1 = new Person("John", "Smith", 30, Instant.now());
        Person person2 = new Person("James", "Bond", 45, Instant.now());
        Person person3 = new Person("Jim", "Bold", 19, Instant.now());
        Person person4 = new Person("Anita", "Kegler", 45, Instant.now());
        Person person5 = new Person("Mark", "Down", 16, Instant.now());

        hashtable.put(0.9, person5);
        hashtable.put(1.5, person1);
        hashtable.put(6.5, person2);
        hashtable.put(3.2, person4);
        hashtable.put(8.5, person3);
//        hashtable.put(8.5, null); // not null
//        hashtable.put(null, null); // not null
    }
}
