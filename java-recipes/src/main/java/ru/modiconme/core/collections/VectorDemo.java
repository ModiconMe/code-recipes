package ru.modiconme.core.collections;

import java.util.Vector;

// vector is synchronized
// DEPRECATED -> use ArrayList()
public class VectorDemo {
    public static void main(String[] args) {
        Vector<Integer> list = new Vector<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.firstElement());
        System.out.println(list.lastElement());
        list.remove(2);
        System.out.println(list);
        System.out.println(list.get(1));
    }
}
