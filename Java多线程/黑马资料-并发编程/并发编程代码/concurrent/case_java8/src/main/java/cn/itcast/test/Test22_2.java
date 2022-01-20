package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Title: Test22_2</p>
 * <p>Description: \</p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2022/1/13</p>
 *
 * @author : xhjing
 * @version :1.0.0
 */
@Slf4j(topic = "c.Test22_2")
public class Test22_2 {
    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                reentrantLock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.info("被打断，没有获取到锁");
                return;
            }
            try {
                log.info("获得到锁");
            }finally {
                reentrantLock.unlock();
            }
        }, "t1");

        reentrantLock.lock();
        t1.start();
        t1.interrupt();
    }
}