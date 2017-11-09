package com.lbv4.bl.cdn;

import com.lbv4.utils.SecurityManager;

public class CdnViet implements CdnInterface {
    private static final String _SERVER_KEY_ = "crntrvtgcnrbt";
    private static final String _STREAM_TEMPLATE_ = "http://xqdgsfbm.cdnviet.com/xwhgpwr/_definst_/mp4:xwhgpwr/%file%/playlist.m3u8?t=%token%&e=%expired%";
    private static final String _APP_NAME_ = "rkjhwcsd";
    
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
        String token = SecurityManager.md5_string(_APP_NAME_ + standardFilename(path) + ip + String.valueOf(expired) + _SERVER_KEY_);
        
        return _STREAM_TEMPLATE_.replaceAll("%file%", path).replaceAll("%token%", token).replaceAll("%expired%", String.valueOf(expired));
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println(System.currentTimeMillis() / 1000);
        CdnViet obj = new CdnViet();
//        System.out.println(obj.getStream("21.12.21.12", "demo.mp4"));
    }
}
