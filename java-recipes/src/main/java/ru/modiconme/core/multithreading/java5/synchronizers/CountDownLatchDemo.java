package ru.modiconme.core.multithreading.java5.synchronizers;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    static CountDownLatch countDownLatch = new CountDownLatch(3); // 3 countDown until thread can work

    private static void marketStuffIsOnPlace() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Market staff came to work");
        countDownLatch.countDown();
        System.out.println("countDownLatch = " + countDownLatch.getCount());
    }

    private static void everythingIsReady() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Everything is ready to open market");
        countDownLatch.countDown();
        System.out.println("countDownLatch = " + countDownLatch.getCount());
    }

    private static void openTheDoor() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Open the door");
        countDownLatch.countDown();
        System.out.println("countDownLatch = " + countDownLatch.getCount());
    }

    public static void main(String[] args) {
        new Thread(new Friend("Bob", countDownLatch)).start();
        new Thread(new Friend("Jim", countDownLatch)).start();
        new Thread(new Friend("John", countDownLatch)).start();
        new Thread(new Friend("James", countDownLatch)).start();

        try {
            marketStuffIsOnPlace();
            everythingIsReady();
            openTheDoor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Friend implements Runnable {

    String name;
    CountDownLatch countDownLatch;

    public Friend(String name, CountDownLatch countDownLatch) {
        this.name = name;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println(name + " is waiting");
        try {
            countDownLatch.await();
            System.out.println(name + " came to the market");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
