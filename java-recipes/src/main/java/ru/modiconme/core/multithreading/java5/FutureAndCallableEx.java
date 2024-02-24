package ru.modiconme.core.multithreading.java5;

import java.util.Scanner;
import java.util.concurrent.*;

public class FutureAndCallableEx {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter factorial:");
        int f = scanner.nextInt();
        Callable<Integer> factorial = () -> {
            int res = 1;
            for (int i = 1; i <= f; i++) {
                res *= i;
            }
            Thread.sleep(2000);
            return res;
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> submit = executorService.submit(factorial);
//        executorService.awaitTermination(5, TimeUnit.SECONDS);
        try {
            System.out.println("Task ends? " + submit.isDone());
            System.out.println(submit.get()); // if Runnable -> return null
            System.out.println("Task ends? " + submit.isDone());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}

class SumNumbers {

    private static long result = 1_000_000_000L;
    private static int threadCount = 10;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        long res = 0L;
        for (int i = 0; i < 10; i++) {
            long start = i * result / threadCount + 1;
            long end = (i + 1) * result / threadCount;
            Future<Long> submit = executorService.submit(new Sum(start, end));
            res += submit.get();
        }
        executorService.shutdown();
        System.out.println(res);
    }
}

class Sum implements Callable<Long> {

    private long start;
    private long end;

    public Sum(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Long call() throws Exception {
        System.out.println("Start " + start);
        System.out.println("end " + end);
        long result = 0L;
        for (long i = start; i <= end; i++) {
            result += i;
        }
        return result;
    }
}
