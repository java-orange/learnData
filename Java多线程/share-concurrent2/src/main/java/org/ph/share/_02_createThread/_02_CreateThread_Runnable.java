package org.ph.share._02_CreateThread;


public class _02_CreateThread_Runnable {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> System.out.println("我是子线程")
        );
        thread.start();
        System.out.println("main 结束");
    }
}
