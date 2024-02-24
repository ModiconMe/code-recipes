package ru.modiconme.core.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ArrayList - resizable array that can store duplicate and null
public class ArrayListDemo {
    public static void main(String[] args) {

        // create
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>(200); // capacity = 200, size = 0
        List<Integer> list3 = new ArrayList<>(list2);

        // from array. You cannot add el to that list - its coupled with array that created from
        String[] strings = {"hello", "good", "boy"};
        List<String> list = Arrays.asList(strings); // fixed size
//        list.add("booy"); // cannot add
        System.out.println(list);

        // create immutable list
        List<Integer> list5 = List.of(1, 2, 3); // immutable list
        List<Integer> list6 = List.copyOf(list1); // immutable

        // methods
        for (int i = 0; i < 100; i++) {
            list1.add(i);
        }

        list1.add(100, 1);
//        list1.add(101, 1); // index 101 not exist
        list1.set(50, 1); // set el on index 50
        list1.addAll(list3);

        list1.remove(50); // remove el on index 50
        list1.remove(Integer.valueOf(50)); // remove object 50 - need equals and hashcode override

        System.out.println("get(): " + list1.get(10));
        System.out.println("indexOf(): " + list1.indexOf(15)); // index of el 15 - need equals and hashcode override
        System.out.println("isEmpty(): " + list1.isEmpty());
        System.out.println("contains(): " + list1.contains(15)); // need equals and hashcode override

        System.out.println("removeAll(): " + list1.removeAll(list2)); // remove elements from list1 that contains in list2
        System.out.println("containsAll(): " + list1.containsAll(list2)); // list1 contains all elements in list2

        List<Integer> list4 = list1.subList(5, 10);
//        list1.add(100); // cannot modify after sublist()
        list4.add(100); // can add and its will be shown in original list (100 add on index 11)
        System.out.println("sublist(): " + list1);
        System.out.println("sublist(): " + list4);

        System.out.println("toArray(): " + Arrays.toString(list4.toArray(new Integer[0]))); // create array that will be sized by list

        list1.clear(); // delete all
        System.out.println("clear(): " + list1);

    }
}
