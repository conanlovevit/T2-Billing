package com.xmio.wowza.utils;

import java.util.Date;

public class TokenManager {
    private final static String _SERVER_KEY_ = "cribUBRVUVRU54584ycbrubCUcreu";
    private final static long _SERVER_TIMEOUT_ = 3600l;  // 1 hour
    private final static String _APP_NAME_ = "vod";
    
    private static String standardFilename(String str) {
        String ret = str;
        if (ret.indexOf('.') >= 0) {
            ret = str.split(".", 2)[0];
        }
        if (ret.indexOf('-') >= 0) {
            ret = str.split("-", 2)[0];
        }
        return ret;
    }
    
    private static String genToken(String path, String ip, String expired) throws Exception {
        return SecurityManager.md5_string(_APP_NAME_ + standardFilename(path) + ip + expired + _SERVER_KEY_);    
    }
    
    public static String genWowzaStreamingCard(String path, String ip) throws Exception {
        // gen expired
        long expired = System.currentTimeMillis() / 1000 + _SERVER_TIMEOUT_;
        
        // gen token
        String token = genToken(path, ip, String.valueOf(expired));
        
        return "t=" + token + "&e=" + expired;
    }
    
    public static boolean verify(String query, String path, String ip) {
        try {
            String[] lStr = query.split("&");
            // get param
            String token = null;
            String expired = null;
            for (int i = 0; i < lStr.length; i++) {
                String[] params = lStr[i].split("=", 2);
                if (params.length == 2) {
                    if (params[0].equalsIgnoreCase("t"))
                        token = params[1];
                    if (params[0].equalsIgnoreCase("e"))
                        expired = params[1];
                }
            }
            if (token == null || expired == null) {
                throw new RuntimeException("data == null || expired == null");
            }
            
            if (genToken(path, ip, expired).equalsIgnoreCase(token) == false) throw new Exception("Faild verify!");
                
            return true;
        } catch (Exception ex) {
            System.out.println("TokenManager --> verify faild: " + query + ", " + path + ", " + ip + " --> " + ex.getMessage());
            return false;
        }
    }
}
