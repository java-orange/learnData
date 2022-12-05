package com.itheima.four;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
    客户端
 */
public class Client {
    public static void main(String[] args) {
        try {
            // 1、请求与服务端的Socket对象链接
            Socket socket = new Socket("127.0.0.1" , 9999);
            // 2、得到一个打印流
            PrintStream ps = new PrintStream(socket.getOutputStream());
            // 3、使用循环不断的发送消息给服务端接收
            Scanner sc = new Scanner(System.in);
            while(true){
                System.out.print("请说：");
                String msg = sc.nextLine();
                ps.println(msg);
                ps.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
