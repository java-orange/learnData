package com.hvisions.bio.serialPort;

import java.io.IOException;

public interface HvSerialPort {

    /**
     * 打开串口
     *
     * @return HvSerialPort对象
     */
    HvSerialPort open() throws IOException;

    /**
     * 关闭串口
     *
     * @return HvSerialPort对象
     */
    HvSerialPort close();

    /**
     * 是否已连接
     *
     * @return true or false
     */
    boolean isConnected();
    /**
     * 读取串口数据
     *
     * @param command 发送
     * @return
     */
    byte[] read(byte[] command);

    void write(byte[] command);
}
