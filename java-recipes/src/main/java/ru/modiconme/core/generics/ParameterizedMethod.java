package ru.modiconme.core.generics;

import java.util.ArrayList;
import java.util.List;

public class ParameterizedMethod {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Integer secondElement = GenMethod.getSecondElement(list);
        System.out.println(secondElement);
    }
}

class GenMethod<V> {
    public V getFirstElement(List<V> arrayList) {
        return arrayList.get(1);
    }
    public static <T> T getSecondElement(List<T> arrayList) { // <T> - generic, T - return type
        return arrayList.get(1);
    }
}
