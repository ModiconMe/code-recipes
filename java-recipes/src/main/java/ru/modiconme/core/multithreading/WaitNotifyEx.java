package ru.modiconme.core.multithreading;

public class WaitNotifyEx {
    public static void main(String[] args) {
        Market market = new Market();
        Runnable producer = () -> {
            while (true) {
                market.produce();
            }
        };
        Runnable consumer = () -> {
            while (true) {
                market.consume();
            }
        };

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}

class Market {
    static int breadCount = 0;

    private Object lock = new Object();

    public synchronized void consume() {
        while (breadCount <= 0) { // use while because thread can wake up without notify()
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        breadCount--;
        System.out.println("Consume 1 bread. bread count = " + breadCount);
        notify();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public void consume() {
//        synchronized (lock) {
//            while (breadCount <= 0) {
//                try {
//                    lock.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            breadCount--;
//            System.out.println("Consume 1 bread. bread count = " + breadCount);
//            lock.notify();
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public synchronized void produce() {
        while (breadCount >= 10) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        breadCount++;
        System.out.println("Produce 1 bread. bread count = " + breadCount);
        notify();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
