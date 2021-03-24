package com.atguigu.single;

/**
 * @author gcq
 * @Create 2020-09-22
 */
public class Singleton1 {
    /**
     * 1、构造器私有化
     * 2、自行创建，并且用静态变量保存
     * 3、向外提供实例
     * 4、强调这是一个单例，我们可以用final修改
     */
    private Singleton1() {

    }
    public static final Singleton1 INSTANCE = new Singleton1();

}