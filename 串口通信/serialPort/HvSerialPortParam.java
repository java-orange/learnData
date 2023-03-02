package com.hvisions.bio.serialPort;

public class HvSerialPortParam {
    public static int DATABITS_5 = 5;
    public static int DATABITS_6 = 6;
    public static int DATABITS_7 = 7;
    public static int DATABITS_8 = 8;

    public static int PARITY_NONE = 0;
    public static int PARITY_ODD = 1;
    public static int PARITY_EVEN = 2;
    public static int PARITY_MARK = 3;
    public static int PARITY_SPACE = 4;

    public static int STOPBITS_1 = 1;
    public static int STOPBITS_2 = 2;
    public static int STOPBITS_1_5 = 3;

    public static int FLOWCONTROL_NONE = 0;
    public static int FLOWCONTROL_RTSCTS_IN = 1;
    public static int FLOWCONTROL_RTSCTS_OUT = 2;
    public static int FLOWCONTROL_XONXOFF_IN = 4;
    public static int FLOWCONTROL_XONXOFF_OUT = 8;

    private String port; // 串口端口号
    private int bitrate = 9600; // 比特率
    private int dataBits = DATABITS_8; // 数据位数
    private int stopBits = STOPBITS_1; // 停止位数
    private int parity = PARITY_NONE; // 校验方式

    private Boolean DTR;

    public String port() {
        return port;
    }

    public HvSerialPortParam port(String port) {
        this.port = port;

        return this;
    }

    public int bitrate() {
        return bitrate;
    }

    public HvSerialPortParam bitrate(int bitrate) {
        this.bitrate = bitrate;

        return this;
    }

    public int dataBits() {
        return dataBits;
    }

    public HvSerialPortParam dataBits(int dataBits) {
        this.dataBits = dataBits;

        return this;
    }

    public int stopBits() {
        return stopBits;
    }

    public HvSerialPortParam stopBits(int stopBits) {
        this.stopBits = stopBits;

        return this;
    }

    public int parity() {
        return parity;
    }

    public HvSerialPortParam parity(int parity) {
        this.parity = parity;

        return this;
    }

    public Boolean DTR() {
        return DTR;
    }

    public HvSerialPortParam DTR(Boolean DTR) {
        this.DTR = DTR;
        return this;
    }
}
