package ru.modiconme.core.multithreading.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SyncCollectionEx {
    public static void main(String[] args) {
        List<Integer> source = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            source.add(i);
        }
//        List<Integer> target = new ArrayList<>();
        List<Integer> target = Collections.synchronizedList(new ArrayList<>());

        Runnable runnable = () -> target.addAll(source);

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(target.size());

    }
}

class SyncCollectionRemoveEx {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> source = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            source.add(i);
        }
        List<Integer> syncList = Collections.synchronizedList(source);

        Runnable runnable1 = () -> {
            synchronized (syncList) { // iterator has not lock the list -> Concurrent exception
                Iterator<Integer> iterator = syncList.iterator();
                while (iterator.hasNext()) {
                    System.out.println(iterator.next());
                }
            }
        };

        Runnable runnable2 = () -> {
            syncList.remove(10);
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(syncList);

    }
}
