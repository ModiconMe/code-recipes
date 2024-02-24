package ru.modiconme.core.multithreading;

public class DaemonThreadsDemo {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " increment " + i);
            }
        };
        Runnable daemonRunnable = () -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println("Daemon " + Thread.currentThread().getName() + " increment " + i);
            }
        };

        Thread thread = new Thread(runnable);
        Thread daemonThread = new Thread(daemonRunnable);
        daemonThread.setDaemon(true);

        thread.start();
        daemonThread.start();
    }
}
