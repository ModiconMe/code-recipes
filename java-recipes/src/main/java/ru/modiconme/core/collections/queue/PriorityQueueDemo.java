package ru.modiconme.core.collections.queue;

import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        Queue<Integer> queue = new PriorityQueue<>();

        queue.add(2);
        queue.add(3);
        queue.add(1);
        queue.add(100);
        queue.add(-100);

        System.out.println(queue); // no priority
        System.out.println(queue.poll()); // priority
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}
