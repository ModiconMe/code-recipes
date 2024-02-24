package ru.modiconme.core.collections;

import java.util.*;

public class MapDemo {

    private static Map<Integer, String> hashMap = new HashMap<>(); // no order garantee = hashCode
    private static Map<Integer, String> linkedHashMap = new LinkedHashMap<>(); // save order put
    private static SortedMap<Integer, String> treeMap = new TreeMap<>(); // sort key order, key not null (Comparable, Comparator)

    static {
        hashMap.put(4, null);
        hashMap.put(5, null);
        hashMap.put(3, "B"); // override
        hashMap.put(2, "D");
        hashMap.put(null, "C");
        linkedHashMap.put(4, null);
        linkedHashMap.put(5, null);
        linkedHashMap.put(3, "B"); // override
        linkedHashMap.put(2, "D");
        linkedHashMap.put(null, "C");
        treeMap.put(3, null);
        treeMap.put(5, null);
        treeMap.put(4, "B");
        treeMap.put(1, "D");
        treeMap.put(2, "C");
        System.out.println("HashMap: " + hashMap);
        System.out.println("LinkedHashMap: " + linkedHashMap);
        System.out.println("TreeMap: " + treeMap);
    }

    public static void main(String[] args) {

        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        for (Map.Entry<Integer, String> entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

}
