package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * <p>Title: Test2_1</p>
 * <p>Description: </p>
 * <p>Company: www.h-visions.com</p>
 * <p>create date: 2021/12/18</p>
 *
 * @author : xhjing
 * @version :1.0.0
 */

@Slf4j(topic = "c.Test2_1")
public class Test2_1 {

    public void test01() throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running...");
                Thread.sleep(1000);
                return 100;
            }
        });
        Thread thread = new Thread(futureTask,"t1");
        thread.start();
        Integer integer = futureTask.get(2000, TimeUnit.SECONDS);
        log.debug("value is : {}", integer);
    }

    public void test02() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            log.debug("running...");
            Thread.sleep(1000);
            return 10;
        });

        new Thread(futureTask, "t2").start();

        Integer integer = futureTask.get();
        log.debug("value is -> {}", integer);


    }

}