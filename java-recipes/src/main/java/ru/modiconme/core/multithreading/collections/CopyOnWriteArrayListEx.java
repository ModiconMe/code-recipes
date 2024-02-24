package ru.modiconme.core.multithreading.collections;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * When write or update -> new copy of list, on read -> no copy
 */
public class CopyOnWriteArrayListEx {
    public static void main(String[] args) throws InterruptedException {
//        List<String> strings = new ArrayList<>();
        List<String> strings = new CopyOnWriteArrayList<>();
        strings.add("Jim");
        strings.add("John");
        strings.add("Julia");
        strings.add("Maria");
        System.out.println(strings);

        Runnable runnable1 = () -> {
//            strings.forEach(System.out::println);
            Iterator<String> iterator = strings.iterator();
            while (iterator.hasNext()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(iterator.next());
            }
        };

        Runnable runnable2 = () -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            strings.remove(3);
            strings.add("Angelo");
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(strings);
    }
}
