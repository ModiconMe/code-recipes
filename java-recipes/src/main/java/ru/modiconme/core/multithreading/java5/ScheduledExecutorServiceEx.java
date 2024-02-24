package ru.modiconme.core.multithreading.java5;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceEx {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println("Thread " + Thread.currentThread().getName() + " begins work");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread " + Thread.currentThread().getName() + " ends work");
        };

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
//        executorService.schedule(runnable, 3, TimeUnit.SECONDS);
//        executorService.scheduleAtFixedRate(runnable, 3, 1, TimeUnit.SECONDS); // period = start_task1 to start_task2
        executorService.scheduleWithFixedDelay(runnable, 3, 1, TimeUnit.SECONDS); // period = end_task1 to start_task2

        Thread.sleep(10000);
        executorService.shutdown();
        System.out.println("Main ends");
    }
}
