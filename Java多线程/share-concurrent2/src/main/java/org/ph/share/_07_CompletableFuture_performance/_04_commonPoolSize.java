package org.ph.share._07_CompletableFuture_performance;

import java.util.concurrent.ForkJoinPool;

public class _04_commonPoolSize {
    public static void main(String[] args) {

        // Returns the number of processors available to the Java virtual machine
        // 查看电脑 线程核心数量  此电脑，6核12线程
        System.out.println(Runtime.getRuntime().availableProcessors());
        // 查看 当前线程数
        System.out.println(ForkJoinPool.commonPool().getPoolSize());
        // 查看 最大线程数
        System.out.println(ForkJoinPool.getCommonPoolParallelism());

    }
}
