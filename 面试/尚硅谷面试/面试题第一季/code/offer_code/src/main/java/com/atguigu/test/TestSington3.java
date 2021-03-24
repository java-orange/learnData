package com.atguigu.test;

import com.atguigu.single.Singleton3;

/**
 * @author gcq
 * @Create 2020-09-22
 */
public class TestSington3 {
    public static void main(String[] args) {
        Singleton3 s = Singleton3.INSTANCE;
        System.out.println(s);
    }
}