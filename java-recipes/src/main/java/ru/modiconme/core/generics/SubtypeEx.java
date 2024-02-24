package ru.modiconme.core.generics;

public class SubtypeEx {
    public static void main(String[] args) {
        X x = new Y();
//        List<X> list = new ArrayList<Y>(); // not allowed
//        List<Y> list = new ArrayList<X>(); // not allowed
    }
}

class X {

}

class Y extends X {

}