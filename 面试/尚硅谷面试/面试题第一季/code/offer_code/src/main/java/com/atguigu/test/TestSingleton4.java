package com.atguigu.test;

import com.atguigu.single.Singleton4;

import java.util.concurrent.*;

/**
 * @author gcq
 * @Create 2020-09-22
 */
public class TestSingleton4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
/*        Singleton4 instance = Singleton4.getInstance();
        Singleton4 instance2 = Singleton4.getInstance();
        System.out.println(instance);
        System.out.println(instance2);
        System.out.println(instance == instance2); //true*/

        Callable<Singleton4> c = new Callable<Singleton4>(){
            public Singleton4 call() throws Exception {
                return Singleton4.getInstance();
            }
        };
        ExecutorService es = Executors.newFixedThreadPool(2);
        Future<Singleton4> f1 = es.submit(c);
        Future<Singleton4> f2 = es.submit(c);

        Singleton4 s1 = f1.get();
        Singleton4 s2 = f2.get();

        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
    }
}