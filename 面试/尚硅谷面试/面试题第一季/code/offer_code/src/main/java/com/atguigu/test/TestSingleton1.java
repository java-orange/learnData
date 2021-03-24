package com.atguigu.test;

import com.atguigu.single.Singleton1;

/**
 * @author gcq
 * @Create 2020-09-22
 */
public class TestSingleton1 {
    public static void main(String[] args) {
        Singleton1 instance = Singleton1.INSTANCE;
        System.out.println(instance);
    }
}