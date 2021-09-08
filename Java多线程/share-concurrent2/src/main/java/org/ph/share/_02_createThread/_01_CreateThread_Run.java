package org.ph.share._02_CreateThread;


public class _01_CreateThread_Run {
    public static void main(String[] args) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                System.out.println("我是子线程");
            }
        };
        thread.start();
        System.out.println("main 结束");
    }
}
