package ru.modiconme.core.multithreading;

public class ThreadMethods {
    static Runnable runnable1 = () -> System.out.println(
            "Hello world " + Thread.currentThread()); // [thread_name, priority, parent_thread_name]
    static Thread thread1 = new Thread(runnable1);
    static Thread thread2 = new Thread(runnable1);
    static Thread sleepThread = new Thread(
            () -> {
                try {
                    Thread.sleep(2000);
                    System.out.println("Wake up");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
    );

    public static void main(String[] args) {

        // setName() setPriority()
        System.out.println("Name: " + thread1.getName()); // Thread-0 - just name of thread
        System.out.println("Priority: " + thread1.getPriority()); // 5 - no garantee, jvm choose thread by itself
        System.out.println("Name: " + thread2.getName()); // Thread-1
        System.out.println("Priority: " + thread2.getPriority()); // 5

        thread1.setName("MyThread1");
        thread1.setPriority(Thread.MAX_PRIORITY);
        System.out.println("Name: " + thread1.getName()); // MyThread1
        System.out.println("Priority: " + thread1.getPriority()); // 10

        thread1.start();
        System.out.println(thread1.getState()); // RUNNABLE

        sleepThread.start();
        System.out.println(sleepThread.getState()); // RUNNABLE

        // sleep()
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(sleepThread.getState()); // TIMED_WAITING

        // join
        try {
            sleepThread.join(); // wait until sleepThread TERMINATED and then resume main thread
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // state - NEW -> RUNNABLE -> TERMINATED;
        // RUNNABLE - ready or running (ready - wait for running)
        // TIMED_WAITING - sleep(500), wait(500)
        // WAITING - wait()
        System.out.println(thread1.getState()); // TERMINATED
        System.out.println(thread2.getState()); // NEW
    }
}

