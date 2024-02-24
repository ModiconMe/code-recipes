package ru.modiconme.core.multithreading.collections;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
/*
 * segment (bucket) lock. Thread can work concurrent in different buckets
 */
public class ConcurrentHashMapEx {
    public static void main(String[] args) throws InterruptedException {
//        HashMap<Integer, String> map = new HashMap<>();
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "'Jim'");
        map.put(2, "'John'");
        map.put(3, "'Bob'");
        map.put(4, "'James'");
//        map.put(null, "'James'"); // not allowed
//        map.put(5, null); // not allowed
//        map.put(null, null); // not allowed
        System.out.println(map);

        Runnable runnable1 = () -> {
            Iterator<Integer> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Integer next = iterator.next();
                System.out.println(next + ":" + map.get(next));
            }
        };

        Runnable runnable2 = () -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(6, "Johny");
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(map);
    }
}
