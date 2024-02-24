package ru.modiconme.core.multithreading.java5.synchronizers;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore callBox = new Semaphore(2); // only 2 thread can use this semaphore

        new Thread(new Person("Jim", callBox)).start();
        new Thread(new Person("Bob", callBox)).start();
        new Thread(new Person("John", callBox)).start();
        new Thread(new Person("James", callBox)).start();
    }
}

class Person implements Runnable {
    String name;
    Semaphore semaphore;

    public Person(String name, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        System.out.println(name + " waiting...");
        try {
            semaphore.acquire(); // thread enter to the resource -> counter -1
            System.out.println(name + " use telephone");
            Thread.sleep(2000);
            System.out.println(name + " ends call");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(); // release
        }
    }
}
