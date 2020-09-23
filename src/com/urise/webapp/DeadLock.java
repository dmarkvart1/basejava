package com.urise.webapp;

public class DeadLock {
    public static final Object Lock1 = new Object();
    public static final Object Lock2 = new Object();

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();

        thread1.start();
        thread2.start();
    }

    private static class Thread1 extends Thread {
        public void run() {
            synchronized (Lock1) {
                System.out.println("Thread1 удерживает LOCK 1...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread1 ждет Lock 2...");
                synchronized (Lock2) {
                    System.out.println("Thread1  удерживает Lock1 и Lock2...");
                }
            }
        }
    }

    private static class Thread2 extends Thread {
        public void run() {
            synchronized (Lock2) {
                System.out.println("Thread2 удерживает LOCK 2...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread2 ждет Lock1...");
                synchronized (Lock1) {
                    System.out.println("Thread2  удерживает Lock 1 and Lock 2...");
                }
            }
        }
    }
}