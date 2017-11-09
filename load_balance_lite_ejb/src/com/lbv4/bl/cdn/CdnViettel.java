package com.lbv4.bl.cdn;

import com.lbv4.utils.SecurityManager;

public class CdnViettel implements CdnInterface {
    private static final String _SERVER_KEY_ = "key_test";
    private static final String _STREAM_TEMPLATE_ = "http://testvod.db8a5b56.viettel-cdn.vn/%token%%expired%/%file%/playlist.m3u8";
    
    private static final long _TIMEOUT_ = 3600;
    
    @Override
    public String getStream(String ip, String path) throws Exception {
        // gen expired
        long expired = System.currentTimeMillis() / 1000 + _TIMEOUT_;
//        expired = 1921369571L;
        
        // gen token
        String plain = ip + ":" + _SERVER_KEY_ + ":" + String.valueOf(expired) + ":" + "/" + path;
        System.out.println(plain);
        String token = SecurityManager.md5_string(plain);
        
        return _STREAM_TEMPLATE_.replaceAll("%file%", path).replaceAll("%token%", token).replaceAll("%expired%", String.valueOf(expired));
    }
    
    public static void main(String[] args) throws Exception {
        CdnViettel obj = new CdnViettel();
        System.out.println(obj.getStream("113.160.73.6", "test_vod.mp4"));
    }
}
