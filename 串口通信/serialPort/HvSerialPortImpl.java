package com.hvisions.bio.serialPort;


import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class HvSerialPortImpl implements Runnable, SerialPortEventListener, HvSerialPort {
    Logger logger = LoggerFactory.getLogger(HvSerialPortImpl.class);

    /**
     * 名称，应用程序设定
     */
    private String name;
    /**
     * 串口连接参数
     */
    private HvSerialPortParam param;
    /**
     * RS232串口
     */
    private SerialPort serialPort;

    /**
     * 控制数据读取
     */
    private Semaphore sem = new Semaphore(1, true);

    /**
     * 存储读取结果
     */
    private byte[] result;

    public HvSerialPortImpl(String name, HvSerialPortParam param) {
        this.name = name;
        this.param = param;
    }

    @Override
    public HvSerialPort open() throws IOException {
        if (serialPort != null) {
            close();
        }

        CommPortIdentifier portId = getPortId(param.port());

        if (portId == null) {
            throw new IOException("There is no serial port " + param.port());
        }

        try {
            // open:打开串口
            serialPort = (SerialPort) portId.open(name, 3000);

            // 设置串口监听
            serialPort.addEventListener(this);
            // 设置串口数据时间有效(可监听)
            serialPort.notifyOnDataAvailable(true);
            // 设置串口通讯参数
            // 波特率，数据位，停止位和校验方式
            serialPort.setSerialPortParams(param.bitrate(), param.dataBits(), param.stopBits(), param.parity());
            serialPort.setDTR(param.DTR());
            logger.info("Open serial port {} successfully", param.port());
        } catch (TooManyListenersException | UnsupportedCommOperationException | PortInUseException e) {
            logger.error(e.getMessage(), e);
            serialPort = null;

            throw new IOException(e);
        }

        return this;
    }

    @Override
    public HvSerialPort close() {
        if (serialPort != null) {
            serialPort.close();
        }

        serialPort = null;

        return this;
    }

    @Override
    public boolean isConnected() {
        return serialPort != null;
    }

    @Override
    public synchronized byte[] read(byte[] command) {
        if (serialPort == null) {
            try {
                open();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            sem.acquire();
            sendMsg(command);
            sem.release();
            TimeUnit.SECONDS.sleep(1);
            if (sem.tryAcquire(5, TimeUnit.SECONDS)) {
                if (result != null) {
                    return result;
                }
            } else {
                logger.error("Read result from serial port {} timeout", param.port());
            }

            throw new RuntimeException("Read data failed from serial port");
        } catch (InterruptedException e) {
            logger.error("Read serial port failed", e);
            throw new RuntimeException("Read result from serial port failed: " + e.getMessage());
        } finally {
            sem.release();
        }
    }

    @Override
    public void write(byte[] command) {
        sendMsg(command);
    }

    private CommPortIdentifier getPortId(String port) {
        // 获取系统中所有的通讯端口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            CommPortIdentifier portId = portList.nextElement();
            // 判断是否是串口
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (port.equals(portId.getName())) {
                    return portId;
                }
            }
        }

        return null;
    }

    /**
     * 实现接口SerialPortEventListener中的方法 读取从串口中接收的数据
     *
     * @param event 读取从串口中接收的数据
     */
    @Override
    public void serialEvent(SerialPortEvent event) {

        switch (event.getEventType()) {
            // 通讯中断
            case SerialPortEvent.BI:
                // 溢位错误
            case SerialPortEvent.OE:
                // 帧错误
            case SerialPortEvent.FE:
                // 奇偶校验错误
            case SerialPortEvent.PE:
                // 载波检测
            case SerialPortEvent.CD:
                // 清除发送
            case SerialPortEvent.CTS:
                // 数据设备准备好
            case SerialPortEvent.DSR:
                // 响铃侦测
            case SerialPortEvent.RI:
                // 输出缓冲区已清空
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            // 有数据到达
            case SerialPortEvent.DATA_AVAILABLE:
                readComm();
                break;
            default:
                break;
        }
    }


    /**
     * 接收数据信息
     */
    private void readComm() {

        try (InputStream inputStream = serialPort.getInputStream()){
            sem.acquire();

//            BufferedReader inStream = new BufferedReader(new InputStreamReader(inputStream));
//
//            byte ch = 0;
//            while ((ch = (byte) inStream.read()) != -1) {
//                //出现ASCII=13（回车） ASCII=10时中断while循环，防止IOException异常
//                if ((ch == 13) || (ch == 10)) {
//                    break;
//                } else {
//                    data.add(ch);
//                }
//            }

            int bufflenth = inputStream.available();
            byte[] bytes = null;
            while (bufflenth != 0) {
                // 初始化byte数组为buffer中数据的长度
                bytes = new byte[bufflenth];
                inputStream.read(bytes);
                bufflenth = inputStream.available();
            }
            logger.info("Read data from serial port {} with data size of {} ", param.port(), bytes.length);
            result = bytes;
        } catch (IOException e) {
            logger.error("Read data of serial port failed", e);
        } catch (InterruptedException e) {
            logger.error("sem.acquire() error", e);
        } finally {
            sem.release();
        }

    }


    /**
     * 向串口发送数据
     * @param command 数据信息
     */
    private void sendMsg(byte[] command) {
        result = null;

        try (OutputStream outputStream = serialPort.getOutputStream();){
            outputStream.write(command);
            outputStream.flush();
        } catch (IOException e) {
            logger.error("Write to serial port failed: {}", e.getMessage(), e);
            throw new RuntimeException("Write to serial port failed");
        }
    }

    @Override
    public void run() {

    }
}
