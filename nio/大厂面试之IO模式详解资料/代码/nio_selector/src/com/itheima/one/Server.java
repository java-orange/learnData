package com.itheima.one;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
    目标：NIO非阻塞通信下的入门案例：服务端开发
 */
public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("----服务端启动---");
        // 1、获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        // 2、切换为非阻塞模式
        ssChannel.configureBlocking(false);
        // 3、绑定连接的端口
        ssChannel.bind(new InetSocketAddress(9999));
        // 4、获取选择器Selector
        Selector selector = Selector.open();
        // 5、将通道都注册到选择器上去，并且开始指定监听接收事件
        ssChannel.register(selector , SelectionKey.OP_ACCEPT);
        // 6、使用Selector选择器轮询已经就绪好的事件
        while (selector.select() > 0){
            System.out.println("开始一轮事件处理~~~");
            // 7、获取选择器中的所有注册的通道中已经就绪好的事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            // 8、开始遍历这些准备好的事件
            while (it.hasNext()){
                // 提取当前这个事件
                SelectionKey sk = it.next();
                // 9、判断这个事件具体是什么
                if(sk.isAcceptable()){
                    // 10、直接获取当前接入的客户端通道
                    SocketChannel schannel = ssChannel.accept();
                    // 11 、切换成非阻塞模式
                    schannel.configureBlocking(false);
                    // 12、将本客户端通道注册到选择器
                    schannel.register(selector , SelectionKey.OP_READ);
                }else if(sk.isReadable()){
                    // 13、获取当前选择器上的读就绪事件
                    SocketChannel sChannel = (SocketChannel) sk.channel();
                    // 14、读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int len = 0;
                    while((len = sChannel.read(buf)) > 0){
                        buf.flip();
                        System.out.println(new String(buf.array() , 0, len));
                        buf.clear();// 清除之前的数据
                    }
                }

                it.remove(); // 处理完毕之后需要移除当前事件
            }
        }
    }
}







