package org.ph.share._02_CreateThread;

import java.util.concurrent.TimeUnit;

public class ThreadStateTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread();
        System.out.println("1- " + thread.getState());
        thread.start();
        System.out.println("2- " + thread.getState());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("3- " + thread.getState());
    }
}
