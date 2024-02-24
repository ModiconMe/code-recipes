package ru.modiconme.core.generics;

// type erasure
public class ParameterizedClassAndTypeErasure {
    public static void main(String[] args) {
        Info<Integer> integerInfo = new Info<>(10);
        System.out.println(integerInfo);
        Integer value = integerInfo.getValue(); // TYPE ERASURE - Integer value = (Integer) integerInfo.getValue(); -> auto casting
    }
}

// parameterized
class Info<T> {
//    private static T value; // not allowed
    private T value;

    public Info(T value) { // T -> Object - TYPE ERASURE
        this.value = value;
    }

    public String toString() {
        return "{[" + value + "]}";
    }

    public T getValue() {
        return value;
    }

    /*
     * generics overloading is not allowed
     * after type erasure
     * 1) public void abc(Info<String> info) {} -> public void abc(Info info) {}
     * 2) public void abc(Info<Integer> info) {} -> public void abc(Info info) {}
     */
//    public void abc(Info<String> info) {}
//    public void abc(Info<Integer> info) {}
}

class Pair<V1, V2> {
    private V1 value1;
    private V2 value2;

    public Pair(V1 value1, V2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public V1 getValue1() {
        return value1;
    }

    public V2 getValue2() {
        return value2;
    }
}

//class Parent {
//    public void abc(Info<Integer> info) {}
//}
//
//class Child extends Parent {
//    public void abc(Info<String> info) {}
//}
