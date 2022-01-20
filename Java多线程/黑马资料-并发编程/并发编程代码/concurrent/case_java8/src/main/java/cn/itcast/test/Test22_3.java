package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Title: Test22_3</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2022/1/13</p>
 *
 * @author : xhjing
 * @version :1.0.0
 */

@Slf4j(topic = "c.Test22_3")
public class Test22_3 {

    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            boolean flag = false;
            try {
                flag = reentrantLock.tryLock(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (flag) {
                try {
                    log.info("获取到锁");
                } finally {
                    reentrantLock.unlock();
                    log.info("释放锁");
                }
            } else {
                log.info("获取不到锁");
            }
        }, "t1");


        reentrantLock.lock();
        log.info("获取到锁");
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        reentrantLock.unlock();
        log.info("释放了锁");
    }
}