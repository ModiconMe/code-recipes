package ru.modiconme.recepies;

import lombok.ToString;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Main {
    public static void main(String[] args) {

        A a1 = new A(1);
        A a2 = new A(2);
        A a3 = new A(3);

        B b1 = new B(1, List.of(a1, a2));
        B b2 = new B(2, List.of(a3, a2));

        C c1 = new C(List.of(b1, b2));
        C c2 = new C(List.of(b2));

        Map<A, List<B>> collect = Stream.of(c1, c2)
                .flatMap(c -> c.getB().stream())
                .flatMap(b -> b.getA().stream().map(a -> Map.entry(a, b)))
                .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toList())));
        System.out.println(collect);

    }
}

@Value
class A {
 int val;
}

@Value
class B {
    int val;
    @ToString.Exclude
    List<A> a;
}

@Value
class C {
    List<B> b;
}