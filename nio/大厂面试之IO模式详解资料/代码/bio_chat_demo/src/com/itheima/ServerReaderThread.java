package com.itheima;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerReaderThread extends Thread{
    private Socket socket;
    public ServerReaderThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            // 1、从socket中去获取当前客户端的输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            while((msg = br.readLine())!=null){
                // 2、服务端接收到了客户端的消息之后，是需要推送给当前所有的在线socket
                sendMsgToAllClient(msg);
            }
        }catch (Exception e){
            System.out.println("当前有人下线了！");
            // 从在线socket集合中移除本socket
            Server.allSocketOnLine.remove(socket);
        }
    }

    /**
     * 把当前客户端发来的消息推送给全部在线的socket
     * @param msg
     */
    private void sendMsgToAllClient(String msg) throws Exception {
        for (Socket sk : Server.allSocketOnLine) {
            PrintStream ps = new PrintStream(sk.getOutputStream());
            ps.println(msg);
            ps.flush();
        }
    }
}
