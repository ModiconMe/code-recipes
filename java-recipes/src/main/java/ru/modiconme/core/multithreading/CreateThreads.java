package ru.modiconme.core.multithreading;

/**
 * Concurrency - context switch OR parallelism
 * Sync - sequential execution of instructions
 * Async - parallel execution of instructions (don't wait until end of instruction)
 */
public class CreateThreads {
    public static void main(String[] args) {
        // extends Thread
        Thread thread1 = new MyThread();
        thread1.start();

        // implements Runnable
        Thread thread2 = new Thread(new MyRunnable());
        thread2.start();

        // implements Runnable lambda
        Thread thread3 = new Thread(
                () -> System.out.println("Hello from " + Thread.currentThread().getName())
        );
        thread3.start();
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from " + Thread.currentThread().getName());
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello from " + Thread.currentThread().getName());
    }
}
