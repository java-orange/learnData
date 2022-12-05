package com.itheima;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
    目标：实现客户端的开发

    基本思路：
        1、客户端发送消息给服务端
        2、客户端可能还需要接收服务端发送过来的消息
 */
public class Client {
    public static void main(String[] args) {
        try{
            // 1、创建于服务端的Socket链接
            Socket socket = new Socket("127.0.0.1" , 9999);
            // 4、分配一个线程为客户端socket服务接收服务端发来的消息
            new ClientReaderThread(socket).start();

            // 2、从当前socket管道中得到一个字节输出流对应的打印流
            PrintStream ps = new PrintStream(socket.getOutputStream());
            // 3、接收用户输入的消息发送出去
            Scanner sc = new Scanner(System.in);
            while (true) {
                String msg = sc.nextLine();
                ps.println("波妞："+msg);
                ps.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
