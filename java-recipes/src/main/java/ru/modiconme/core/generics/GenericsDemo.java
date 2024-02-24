package ru.modiconme.core.generics;

import java.util.ArrayList;
import java.util.List;
/*
 * Generics = typesafe & reusable code
 */
public class GenericsDemo {
    public static void main(String[] args) {
        List list = new ArrayList(); // object

        list.add("John");
        list.add("Jim");
        list.add("James");
        list.add("Jamila");
        list.add("Jamila");
        list.add(12); // unsafe -> class cast exception

        for (Object o : list) {
            System.out.println(o + " length " + ((String) o).length());
        }
    }
}

