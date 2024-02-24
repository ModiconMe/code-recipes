package ru.modiconme.core.multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedBlockEx {

    static int count = 0;
    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        sync();
        reentrantLock();
    }

    private static void sync() throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 1000000; i++) {
                SynchronizedBlockEx.incrementWithSync();
            }
        };

        long start = System.currentTimeMillis();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);
        Thread thread4 = new Thread(runnable);
        Thread thread5 = new Thread(runnable);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();

        System.out.println("Execution sync takes: %s".formatted(System.currentTimeMillis() - start));

        System.out.println(count);
        count = 0;
    }

    private static void reentrantLock() throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 1000000; i++) {
                SynchronizedBlockEx.incrementWithLock();
            }
        };

        long start = System.currentTimeMillis();
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);
        Thread thread4 = new Thread(runnable);
        Thread thread5 = new Thread(runnable);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();

        System.out.println("Execution reentrant lock takes: %s".formatted(System.currentTimeMillis() - start));

        System.out.println(count);
    }

    // data race -> need synchronized
    public static void count() {
        for (int i = 0; i < 1000; i++) {
            synchronized (lock) {
                count++;
            }
            System.out.print(count + " ");
        }
    }

    public static void nonSync() {
        System.out.println("Hello " + Thread.currentThread().getName() + " you in nonSync method");
    }

    public static Lock l = new ReentrantLock();

    // data race -> need synchronized
    public static void incrementWithSync() {
        synchronized (lock) {
            count++;
        }
    }

    public static void incrementWithLock() {
        l.lock();
        count++;
        l.unlock();
    }

}
