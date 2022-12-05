package com.itheima;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        File f = new File("C:\\Users\\dlei\\Desktop\\BIO,NIO,AIO\\shiping");
        File[] fs = f.listFiles();
        for (File file : fs) {
            Integer num = Integer.valueOf(file.getName().substring(0 , file.getName().indexOf("."))) + 23;
            file.renameTo(new File(f  , num +"."+ file.getName().substring(file.getName().indexOf("."))));
        }
    }
}
