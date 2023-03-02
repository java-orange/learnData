package com.hvisions.bio.serialPort;

import java.io.IOException;

public class HvSerialPortUtil {
    /**
     * 创建HvSerialPort对象
     *
     * @param name 打开的Serial Port使用的名称
     * @param param Serial Port打开的参数
     *
     * @return HvSerialPort对象
     */
    public static HvSerialPort create(String name, HvSerialPortParam param) {
        return new HvSerialPortImpl(name, param);
    }

    public static void main(String[] args) throws IOException {
        HvSerialPortParam param = new HvSerialPortParam();
        param.port("COM1");

        HvSerialPort port = create("COM1", param);
        port.open();
        System.out.println("port status: " + port.isConnected());

        byte[] command = new byte[10];

        command[0] = 1;
        command[1] = 2;
        command[2] = 3;
        command[3] = 4;

        port.write(command);
        port.close();
        System.out.println("close the serial port");
    }
}
