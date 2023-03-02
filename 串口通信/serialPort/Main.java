package com.hvisions.bio.serialPort;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: Main
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author xhjing
 * @Create 2023/3/1 10:06
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        HvSerialPortParam param = new HvSerialPortParam();
        param.port("COM5").bitrate(115200).DTR(true);

        HvSerialPort port = HvSerialPortUtil.create("单扫串口通信", param);
        port.open();
        System.out.println("port status: " + port.isConnected());

        for (int i = 0; i < 10; i++) {

            byte[] read = port.read("T".getBytes());
            String s = new String(read);
            System.out.println("s = " + s);

            TimeUnit.SECONDS.sleep(1);
        }

        port.close();
        System.out.println("port status: " + port.isConnected());
        System.out.println("done ! ");

        System.out.println("over ");
    }
}
