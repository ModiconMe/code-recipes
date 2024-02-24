package ru.modiconme.core.multithreading;

public class DeadlockEx {
    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();

    public static void main(String[] args) {
        Runnable runnable1 = () -> {
            System.out.println("Thread " + Thread.currentThread().getName() + " try to catch monitor lock1");
            synchronized (DeadlockEx.lock1) {
                System.out.println(Thread.currentThread().getName() + " Inside lock1");
                System.out.println("Thread " + Thread.currentThread().getName() + " try to catch monitor lock2");
                synchronized (DeadlockEx.lock2) {
                    System.out.println(Thread.currentThread().getName() + " Inside lock2");
                }
            }
        };

        // need lock lock1 -> lock2 not lock2 -> lock1 (same as runnable1)
        // sync in same order with different methods
        Runnable runnable2 = () -> {
            System.out.println("Thread " + Thread.currentThread().getName() + " try to catch monitor lock2");
            synchronized (DeadlockEx.lock2) {
                System.out.println(Thread.currentThread().getName() + " Inside lock2");
                System.out.println("Thread " + Thread.currentThread().getName() + " try to catch monitor lock1");
                synchronized (DeadlockEx.lock1) {
                    System.out.println(Thread.currentThread().getName() + " Inside lock1");
                }
            }
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();
    }
}
