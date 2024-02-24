package ru.modiconme.core.collections.map;

import java.time.Instant;
import java.util.TreeMap;

// not synchronized
// key != null, value == null
public class TreeMapDemo {
    public static void main(String[] args) {
        TreeMap<Double, Person> treeMap = new TreeMap<>();

        Person person1 = new Person("John", "Smith", 30, Instant.now());
        Person person2 = new Person("James", "Bond", 45, Instant.now());
        Person person3 = new Person("Jim", "Bold", 19, Instant.now());
        Person person4 = new Person("Anita", "Kegler", 45, Instant.now());
        Person person5 = new Person("Mark", "Down", 16, Instant.now());

        treeMap.put(0.9, person5);
        treeMap.put(1.5, person1);
        treeMap.put(6.5, person2);
        treeMap.put(3.2, person4);
        treeMap.put(8.5, person3);

        System.out.println("TreeMap: " + treeMap);
        System.out.println("tailMap(): " + treeMap.tailMap(3.2, true));
        System.out.println("headMap(): " + treeMap.headMap(3.2, true));
        System.out.println("lastEntry(): " + treeMap.lastEntry());
        System.out.println("firstEntry(): " + treeMap.firstEntry());
    }
}
