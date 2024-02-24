package ru.modiconme.core.java8.defaults;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DefaultMethodEx {

    public static void main(String[] args) {
        /*
         Sort the list names int alphabetical order
         */

        List<String> strings = Arrays.asList("adam", "ken", "alex");

        /*
         * Prior java 8
         */
        Collections.sort(strings);
        System.out.println(strings);

        /*
         * java 8
         */
        strings.sort(Comparator.reverseOrder());
        System.out.println(strings);
    }
}
