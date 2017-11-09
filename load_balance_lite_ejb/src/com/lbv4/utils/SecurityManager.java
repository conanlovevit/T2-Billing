package com.lbv4.utils;

import java.security.MessageDigest;

public class SecurityManager {
    private static String byte2Hex(byte[] buf) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10)
                strbuf.append("0");
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();

    }
    
    private static byte[] md5_byte(String arg) throws Exception {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        md.update(arg.getBytes());
        return md.digest();
    }

    public static String md5_string(String arg) throws Exception {
        return byte2Hex(md5_byte(arg)).toLowerCase();
    }
}
