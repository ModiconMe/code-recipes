package ru.modiconme.core.multithreading.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFutureEx = CompletableFuture.runAsync(() -> System.out.println("Completable future ex"));
        completableFutureEx.get();
    }

    public static void supplyAsyncEx() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Result of supplyAsync";
        });
        String result = supplyAsync.get();
        System.out.println(result);
    }

    // вычисляет результать supplyAsync не блокируя поток
    public static void thenApplyEx1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Result of supplyAsync";
        });
        String result = supplyAsync.get();
        System.out.println(result);

        CompletableFuture<String> thenApply = supplyAsync.thenApply((res) -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Result of thenApply " + res.toUpperCase();
        });
        System.out.println("Not blocked");
        String result2 = thenApply.get();
        System.out.println(result2);
    }

    public static void thenApplyEx2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Result of supplyAsync";
        });
        String result = supplyAsync.get();
        System.out.println(result);

        CompletableFuture<String> thenApply = supplyAsync.thenApply((res) -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Result of thenApply " + res.toUpperCase();
        }).thenApply((res) -> res + " second");

        System.out.println("Not blocked");
        String result2 = thenApply.get();
        System.out.println(result2);
    }

    // производит действие(void) над completable future не блокируя поток
    public static void thenRunEx1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Result of supplyAsync";
        });
        String result = supplyAsync.get();
        System.out.println(result);

        supplyAsync.thenRun(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("Not blocked");
    }

    // производит действие(void) над completable future не блокируя поток и имеет доступ к результату supplyAsync
    public static void thenAcceptEx1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Result of supplyAsync";
        });
        String result = supplyAsync.get();
        System.out.println(result);

        supplyAsync.thenAccept((res) -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + res);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("Not blocked");
    }

}
