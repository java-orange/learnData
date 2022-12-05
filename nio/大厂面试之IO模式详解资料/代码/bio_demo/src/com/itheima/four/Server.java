package com.itheima.four;

import java.net.ServerSocket;
import java.net.Socket;

/**
    目标：开发实现伪异步通信架构
 */
public class Server {
    public static void main(String[] args) {
        try {
            // 1、注册端口
            ServerSocket ss = new ServerSocket(9999);
            // 2、定义一个循环接收客户端的Socket链接请求
            // 初始化一个线程池对象
            HandlerSocketServerPool pool = new HandlerSocketServerPool(3,10);
            while(true){
                Socket socket = ss.accept();
                // 3、把socket对象交给一个线程池进行处理，
                // 把socket封装成一个任务对象交给线程池处理
                Runnable target = new ServerRunnableTarget(socket);
                pool.execute(target);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
