package ru.modiconme.core.multithreading;

public class InterruptEx {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int result = 0;
            for (int i = 0; i < Integer.MAX_VALUE; i+=1) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread is interrupted. Result: " + result);
                    return;
                }
                result += Math.sqrt(i);
            }
            System.out.println("Result is:" + result);
        };

        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }
}
