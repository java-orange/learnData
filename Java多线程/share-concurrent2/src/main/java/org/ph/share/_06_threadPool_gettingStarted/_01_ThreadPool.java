package org.ph.share._06_threadPool_gettingStarted;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class _01_ThreadPool {
    public static void main(String[] args) {

        // 创建只有一个线程的线程池
        Executors.newSingleThreadExecutor();

        // 创建出最大线程池，核心线程数为0，最大线程数为最大
        Executors.newCachedThreadPool();

        // 创造出固定线程数量的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

    }
}
