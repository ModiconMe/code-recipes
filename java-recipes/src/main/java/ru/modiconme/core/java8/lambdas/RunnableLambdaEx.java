package ru.modiconme.core.java8.lambdas;

public class RunnableLambdaEx {

    public static void main(String[] args) {

        System.out.println("Hello from thread: " + Thread.currentThread().getName());

        // anonymous class
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                System.out.println("Hello from thread: " + threadName);
            }
        };
        new Thread(runnable1).start();

        // lambda #1
        Runnable runnable2 = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello from thread: " + threadName);
        };
        new Thread(runnable2).start();

        // lambda #2
        Runnable runnable3 = () -> System.out.println("Hello from thread: " + Thread.currentThread().getName());
        new Thread(runnable3).start();

        // lambda #3
        new Thread(() -> System.out.println("Hello from thread: " + Thread.currentThread().getName())).start();
    }
}
