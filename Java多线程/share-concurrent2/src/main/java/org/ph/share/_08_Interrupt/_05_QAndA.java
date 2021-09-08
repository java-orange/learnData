package org.ph.share._08_Interrupt;

import org.ph.share.SmallTool;

import java.util.concurrent.TimeUnit;

public class _05_QAndA {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            SmallTool.printTimeAndThread("开始睡眠");
            forceSleep(3);
            SmallTool.printTimeAndThread("结束睡眠");
        });
        thread.start();
        thread.interrupt();
    }

    @SuppressWarnings("BusyWait")
    public static void forceSleep(int second) {
        long startTime = System.currentTimeMillis();
        long sleepMills = TimeUnit.SECONDS.toMillis(second);

        while ((startTime + sleepMills) > System.currentTimeMillis()) {
            long sleepTime = startTime + sleepMills - System.currentTimeMillis();
            if (sleepTime <= 0) {
                break;
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
