package ru.modiconme.core.collections.queue;

import java.util.LinkedList;
import java.util.Queue;

public class QueueDemo {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(2);
        queue.add(3);
        queue.add(1);
        queue.add(null);
        queue.add(0); // if queue has limit -> exception
        queue.offer(0); // if queue has limit -> not exception

        System.out.println(queue);
        System.out.println(queue.remove());
        System.out.println(queue);
        System.out.println(queue.remove());
        System.out.println(queue.element());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
//        System.out.println(queue.remove()); // exception
        System.out.println(queue.poll()); // null
//        System.out.println(queue.element()); // exception
        System.out.println(queue.peek()); // null
    }
}
