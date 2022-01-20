package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Title: Test22_1</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2022/1/13</p>
 *
 * @author : xhjing
 * @version :1.0.0
 */
@Slf4j(topic = "c.Test22_1")
public class Test22_1 {
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        try {
            lock.lock();
            log.info("main获得锁");
            m1();
        }finally {
            lock.unlock();
            log.info("main 释放锁");
        }
    }
    private static void m1() {
        try {
            lock.lock();
            log.info("m1 获得锁");
            m2();
        }finally {
            lock.unlock();
            log.info("m1 释放锁");
        }

    }
    private static void m2() {
        try {
            lock.lock();
            log.info("m2 获得锁");
        }finally {
            lock.unlock();
            log.info("m2 释放锁");
        }

    }
}