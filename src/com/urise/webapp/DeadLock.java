package com.urise.webapp;

public class DeadLock {
    public static final Object Lock1 = new Object();
    public static final Object Lock2 = new Object();

    public static void main(String[] args) {
        lockMetod(Lock1, Lock2);
        lockMetod(Lock2, Lock1);
    }

    private static void lockMetod(Object o1, Object o2) {
        new Thread(() -> {
            System.out.println(("Ждем " + o1.hashCode()).substring(0, 6));
            synchronized (o1) {
                System.out.println(("Удерживаем " + o1.hashCode()).substring(0, 12));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(("Ждем " + o2.hashCode()).substring(0, 6));
                synchronized (o2) {
                    System.out.println(("Удерживаем " + o2.hashCode()).substring(0, 12));
                }
            }
        }).start();
    }
}