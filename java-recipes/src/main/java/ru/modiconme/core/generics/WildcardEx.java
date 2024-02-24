package ru.modiconme.core.generics;

import java.util.ArrayList;
import java.util.List;

public class WildcardEx {
    public static void main(String[] args) {
        List<?> wildcard = new ArrayList<String>();
        List<? extends Number> boundedWildcard = new ArrayList<Integer>();

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        showListInfo(list);

//        foo(wildcard);
        foo(boundedWildcard);
        foo(list);
    }
    static void showListInfo(List<?> list) {
        System.out.println("List elements: " + list);
    }

    static void foo(List<? extends Number> list) {
        System.out.println("List elements: " + list);
    }
}

