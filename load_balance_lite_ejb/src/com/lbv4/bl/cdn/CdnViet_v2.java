package com.lbv4.bl.cdn;

import com.lbv4.utils.SecurityManager;

public class CdnViet_v2 implements CdnInterface {
    private static final String _SERVER_KEY_ = "abc@#xyd";
    private static final String _STREAM_TEMPLATE_ = "http://vtcmediavod.db943edc.cdnviet.com/%token%%expired%/%file%/playlist.m3u8";
    
    private static final long _TIMEOUT_ = 3600;
    
    private String standardFilename(String str) {
        String ret = str;
        if (ret.indexOf('.') >= 0) {
            ret = str.split(".", 2)[0];
        }
        if (ret.indexOf('-') >= 0) {
            ret = str.split("-", 2)[0];
        }
        return ret;
    }
    
    @Override
    public String getStream(String ip, String path) throws Exception {
        // gen expired
        long expired = System.currentTimeMillis() / 1000 + _TIMEOUT_;
        
        // gen token
        String plain = ip + ":" + _SERVER_KEY_ + ":" + String.valueOf(expired) + ":" + "/" + path;
        System.out.println(plain);
        String token = SecurityManager.md5_string(plain);
        
        return _STREAM_TEMPLATE_.replaceAll("%file%", path).replaceAll("%token%", token).replaceAll("%expired%", String.valueOf(expired));
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println(2979 % 30);
        CdnViet_v2 obj = new CdnViet_v2();
//        System.out.println(obj.getStream("113.160.73.6", "test.mp4"));
        System.out.println(obj.getStream("113.160.73.6", "nas2/media/vod/MOVIE/2015_12/8306_Mat_na___The_Mask_chapter_1_3256000.mp4"));
    }
}
