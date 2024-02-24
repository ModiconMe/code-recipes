package ru.modiconme.core.multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockEx {
    public static void main(String[] args) {
        Call call = new Call();
        Runnable mobileRunnable = call::mobileCall;
        Runnable skypeRunnable = call::skypeCall;
        Runnable whatsAppRunnable = call::whatsApp;

        Thread mobileThread = new Thread(mobileRunnable);
        Thread skypeThread = new Thread(skypeRunnable);
        Thread whatsAppThread = new Thread(whatsAppRunnable);

        mobileThread.start();
        skypeThread.start();
        whatsAppThread.start();
    }

}

class Call {
    private Lock lock = new ReentrantLock();

    void mobileCall() {
        lock.lock();
        try {
            System.out.println("Mobile call starts");
            Thread.sleep(3000);
            System.out.println("Mobile call ends");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    void skypeCall() {
        lock.lock();
        try {
            System.out.println("Skype call starts");
            Thread.sleep(3000);
            System.out.println("Skype call ends");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    void whatsApp() {
        lock.lock();
        try {
            System.out.println("WhatsApp call starts");
            Thread.sleep(3000);
            System.out.println("WhatsApp call ends");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}

class TryLockDemo {
    private static Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        new Employee("Bob", lock);
        new Employee("John", lock);
        new Employee("Nikolay", lock);
        new Employee("Maria", lock);
    }
}

class Employee extends Thread {
    String name;
    private Lock lock;

    public Employee(String name, Lock lock) {
        this.name = name;
        this.lock = lock;
        this.start();
    }

    public void run() {
        if (lock.tryLock()) {
            try {
                System.out.println(name + " wait...");
                lock.lock();
                System.out.println(name + " use");
                Thread.sleep(2000);
                System.out.println(name + " ends");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println(name + " go away");
        }
    }
}
