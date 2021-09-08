package org.ph.share._08_Interrupt;

import org.ph.share.SmallTool;

import java.util.Random;

public class _02_TwoCarCrossBridge {
    public static void main(String[] args) {

        Thread carTwo = new Thread(() -> {
            SmallTool.printTimeAndThread("卡丁2号 准备过桥");
            SmallTool.printTimeAndThread("发现1号在过，开始等待");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                SmallTool.printTimeAndThread("卡丁2号 开始过桥");
            }
            SmallTool.printTimeAndThread("卡丁2号 过桥完毕");
        });


        Thread carOne = new Thread(() -> {
            SmallTool.printTimeAndThread("卡丁1号 开始过桥");
            int timeSpend = new Random().nextInt(500) + 1000;
            SmallTool.sleepMillis(timeSpend);
            SmallTool.printTimeAndThread("卡丁1号 过桥完毕 耗时:" + timeSpend);
//            SmallTool.printTimeAndThread("卡丁2号的状态" + carTwo.getState());
            carTwo.interrupt();
        });

        carOne.start();
        carTwo.start();

    }
}
