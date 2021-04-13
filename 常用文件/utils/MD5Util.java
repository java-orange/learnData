package com.example.mogujie.utils;

import java.security.MessageDigest;
import java.util.List;

public class MD5Util {

    public static String encode(String data) {
        String ret = null;
        try {
            if ((data != null) && (data.length() > 0)) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(data.getBytes("UTF-8"));
                ret = byte2hex(md.digest());
            }
        } catch (Exception e) {
        }

        return ret;
    }

    /**
     * 二行制转字符串
     */
    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }
        return hs.toString();
    }
    

}
