package ru.modiconme.core.multithreading;

public class VolatileEx extends Thread {

    /**
     * use when 1 thread write and other threads only read
     * 1) read from main memory
     * 2) execute some code
     * 3) write to main memory
     * Other threads can read the same value and write the same value
     */

//    volatile boolean b = true; // stored in main memory
    boolean b = true; // stored in core cache

    @Override
    public void run() {
        long counter = 0;
        while (b) {
            counter++;
        }
        System.out.println("Loop is finished. counter = " + counter);
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileEx thread1 = new VolatileEx();
        thread1.start();
        Thread.sleep(3000);
        System.out.println("Time to wake up");
        thread1.b = false;
        thread1.join();
        System.out.println("End of program");
    }
}
