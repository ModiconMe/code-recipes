package ru.modiconme.recepies;

import lombok.Data;
import lombok.ToString;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static ru.modiconme.recepies.Pair.of;

public class Main {
    public static void main(String[] args) {

        List<Pair<Integer, Integer>> edges = List.of(of(0, 2), of(0, 5), of(2, 4), of(1, 6), of(5, 4));
        long count = countPairs(7, edges);
        System.out.println("Count is " + count);
    }

    public static long countPairs(int n, List<Pair<Integer, Integer>> edges) {
        long count = 0L;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        boolean condition = true;
                        for (var edge : edges) {
                            if (edge.getT1() == i && edge.getT2() == j) {
                                condition = false;
                                System.out.println(edge);
                                break;
                            }
                        }
                        if (condition) {
                            count++;
                        }
                    }
                }

        }
        return count;
    }
}

@Value
class Pair<T1, T2> {
    T1 t1;
    T2 t2;

    public static <T1, T2> Pair<T1, T2> of(T1 t1, T2 t2) {
        return new Pair<>(t1, t2);
    }

//    public boolean existBy()
}
