package ru.modiconme.core.multithreading;

public class DataRaceEx {

    static int count = 0;

    public static void main(String[] args) throws InterruptedException {

//        Runnable runnable = DataRaceEx::count;
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                DataRaceEx.increment();
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println(count);
    }

    // data race -> need synchronized
    public static void count() {
        for (int i = 0; i < 1000; i++) {
            count++;
            System.out.print(count + " ");
        }
    }

    // data race -> need synchronized
    public static void increment() {
        count++;
    }

//    public synchronized static void count() {
//        for (int i = 0; i < 3; i++) {
//            count++;
//            System.out.print(count + " ");
//        }
//    }
}