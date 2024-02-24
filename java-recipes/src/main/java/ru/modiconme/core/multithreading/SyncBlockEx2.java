package ru.modiconme.core.multithreading;

public class SyncBlockEx2 {

    static synchronized void mobileCall() {
        System.out.println("Mobile call starts");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Mobile call ends");
    }

//    void mobileCall() {
//        synchronized (lock) { // Object lock = new Object();
//            System.out.println("Mobile call starts");
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Mobile call ends");
//        }
//    }

    static synchronized void skypeCall() {
        System.out.println("Skype call starts");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Skype call ends");
    }

    static synchronized void whatsAppCall() {
        System.out.println("WhatsApp call starts");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("WhatsApp call ends");
    }

    public static void main(String[] args) {
        Runnable mobile = SyncBlockEx2::mobileCall;
        Runnable skype = SyncBlockEx2::skypeCall;
        Runnable whatsApp = SyncBlockEx2::whatsAppCall;

        Thread thread1 = new Thread(mobile);
        Thread thread2 = new Thread(skype);
        Thread thread3 = new Thread(whatsApp);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
