package com.atguigu.test;

import com.atguigu.single.Singleton4;
import com.atguigu.single.Singleton5;

import java.util.concurrent.*;

/**
 * @author gcq
 * @Create 2020-09-22
 */
public class TestSingleton5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
/*        Singleton4 instance = Singleton4.getInstance();
        Singleton4 instance2 = Singleton4.getInstance();
        System.out.println(instance);
        System.out.println(instance2);
        System.out.println(instance == instance2); //true*/

        Callable<Singleton5> c = new Callable<Singleton5>(){
            public Singleton5 call() throws Exception {
                return Singleton5.getInstance();
            }
        };
        ExecutorService es = Executors.newFixedThreadPool(2);
        Future<Singleton5> f1 = es.submit(c);
        Future<Singleton5> f2 = es.submit(c);

        Singleton5 s1 = f1.get();
        Singleton5 s2 = f2.get();

        System.out.println(s1 == s2);
        System.out.println(s1);
        System.out.println(s2);
    }
}