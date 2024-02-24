package ru.modiconme.core.multithreading.java5;

import java.util.concurrent.*;

public class ThreadPoolEx {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> callable1 = () -> {
            System.out.println("Hello " + Thread.currentThread().getName());
            Thread.sleep(1000);
            return 10;
        };
        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            Future<?> submit = executorService.submit(callable1);
            System.out.println("get future " + submit);
            Object o = submit.get();
            System.out.println(o);
        }
        executorService.shutdown();
        System.out.println("Main ends");
    }

}

class AwaitTerminationEx {

    static class Runner {
        void doIt() {
            System.out.println("Thread " + Thread.currentThread().getName() + " begins work");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread " + Thread.currentThread().getName() + " ends work");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();
        Runnable runnable = runner::doIt;
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.submit(runnable);
        }
        executorService.shutdown();
        executorService.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println("Main ends");
    }
}
