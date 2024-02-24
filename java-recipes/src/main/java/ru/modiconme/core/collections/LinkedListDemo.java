package ru.modiconme.core.collections;

import java.util.LinkedList;
import java.util.List;

public class LinkedListDemo {
    public static void main(String[] args) {
        List<String> list1 = new LinkedList<>();

        for (int i = 0; i < 100; i++) {
            list1.add("String " + i);
        }

        System.out.println("Linked list = " + list1);

        System.out.println("get(): " + list1.get(3));
        System.out.println("remove(): " + list1.remove(3));

    }
}
