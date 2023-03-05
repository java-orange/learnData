package com.atguigu.shirotest;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class ShiroMD5 {
    public static void main(String[] args) {
        //密码明文
        String password = "z3";
        //使用md5加密
        Md5Hash md5Hash = new Md5Hash(password);
        System.out.println("md5加密 = " + md5Hash.toHex());
        //带盐的md5加密，盐就是在密码明文后拼接新字符串，然后再进行加密
        Md5Hash md5Hash2 = new Md5Hash(password,"salt");
        System.out.println("带盐的md5加密 = " + md5Hash2.toHex());
        //为了保证安全，避免被破解还可以多次迭代加密，保证数据安全
        Md5Hash md5Hash3 = new Md5Hash(password,"salt",3);
        System.out.println("md5带盐的3次加密 = " + md5Hash3.toHex());
        //使用父类进行加密
        SimpleHash simpleHash = new SimpleHash(
                "MD5",password,"salt",3);
        System.out.println("父类带盐的3次加密 = " + simpleHash.toHex());

    }
}
