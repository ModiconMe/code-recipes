package ru.modiconme.core.collections.queue;

import java.util.ArrayDeque;
import java.util.Deque;

public class DequeDemo {
    public static void main(String[] args) {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.offerFirst(3);
        deque.offerLast(4);

        deque.removeFirst();
        deque.removeLast();
        deque.pollFirst();
        deque.pollLast();

        deque.getFirst();
        deque.getLast();

        System.out.println(deque);
    }
}
