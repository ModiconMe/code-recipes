package ru.modiconme.core.collections;

import java.util.*;

public class SetDemo {

    private static Set<String> hashSet = new HashSet<>();
    private static Set<String> linkedHashSet = new LinkedHashSet<>();
    private static SortedSet<String> treeSet = new TreeSet<>();

    static {
        hashSet.add(null);
        hashSet.add("B");
        hashSet.add("A");
        hashSet.add("C");
        linkedHashSet.add(null);
        linkedHashSet.add("B");
        linkedHashSet.add("A");
        linkedHashSet.add("C");
        treeSet.add("B");
        treeSet.add("A");
        treeSet.add("C");
        System.out.println("HashSet: " + hashSet);
        System.out.println("LinkedHashSet: " + linkedHashSet);
        System.out.println("TreeSet: " + treeSet);
    }

    public static void main(String[] args) {
        hashSet.addAll(linkedHashSet); // union
        hashSet.retainAll(linkedHashSet);
        hashSet.removeAll(linkedHashSet);
    }
}
