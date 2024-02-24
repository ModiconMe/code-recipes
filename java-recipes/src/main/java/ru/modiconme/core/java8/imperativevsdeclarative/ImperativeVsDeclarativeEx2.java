package ru.modiconme.core.java8.imperativevsdeclarative;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImperativeVsDeclarativeEx2 {

    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(1,2,3,4,4,5,5,6,6);

        /**
         * Imperative
         */

        List<Integer> uniqueList = new ArrayList<>();

        for (Integer integer : integerList) {
            if (!uniqueList.contains(integer)) {
                uniqueList.add(integer);
            }
        }

        System.out.println("Unique values using Imperative approach: " + uniqueList);

        /**
         * Declarative
         */
        List<Integer> integers = integerList.stream().distinct().toList();

        System.out.println("Unique values using Imperative approach: " + integers);
    }
}
