package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Title: Test22_5</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2022/1/14</p>
 *
 * @author : xhjing
 * @version :1.0.0
 */
@Slf4j(topic = "c.Test22_5")
public class Test22_5 {

    private static ReentrantLock room = new ReentrantLock();

    private static Condition cigareteeWait = room.newCondition();

    private static Condition TakeOutWait = room.newCondition();

    private static boolean hasCigaretee = false;

    private static boolean hasTakeOut = false;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            room.lock();
            try {
                while (!hasCigaretee) {
                    log.info("没烟，不能干活");
                    try {
                        cigareteeWait.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("干活");
            } finally {
                room.unlock();
            }

        }, "小南");

        Thread t2 = new Thread(() -> {
            room.lock();
            try {
                while (!hasTakeOut) {
                    log.info("饿了，不能干活");
                    try {
                        TakeOutWait.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("吃饭干活");
            } finally {
                room.unlock();
            }
        }, "小女");

        t1.start();
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        room.lock();
        try {
            hasTakeOut = true;
            log.info("送外卖");
            TakeOutWait.signalAll();
        } finally {
            room.unlock();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        room.lock();
        try {
            hasCigaretee = true;
            log.info("送烟");
            cigareteeWait.signalAll();
        } finally {
            room.unlock();
        }
    }
}

