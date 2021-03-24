package com.atguigu.methodParam2;

/**
 * @author gcq
 * @Create 2020-09-29
 */
public class Exam5 {
    static int s;
    int i;
    int j;
    {
        int i = 1;
        i++;
        j++;
        s++;
    }
    public void test(int j) {
        j++;
        i++;
        s++;
    }
    public static void main(String[] args){
        Exam5 obj1 = new Exam5();
        Exam5 obj2 = new Exam5();
        obj1.test(10);
        obj1.test(20);
        obj2.test(30);
        System.out.println(obj1.i + "," + obj1.j + "," + obj1.s);
        System.out.println(obj2.i + "," + obj2.j + "," + obj2.s);
    }

}