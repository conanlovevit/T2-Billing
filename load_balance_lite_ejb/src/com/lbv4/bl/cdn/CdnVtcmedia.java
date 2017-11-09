package com.lbv4.bl.cdn;

import com.lbv4.utils.SecurityManager;

import com.xmio.wowza.utils.TokenManager;

public class CdnVtcmedia implements CdnInterface {
    private static final String _STREAM_TEMPLATE_ = "http://123.30.145.218:1935/vod/_definst_/mp4:%file%/playlist.m3u8?%token%";
    
    @Override
    public String getStream(String ip, String path) throws Exception {
        return _STREAM_TEMPLATE_.replaceAll("%file%", path).replaceAll("%token%", TokenManager.genWowzaStreamingCard(path, ip));
    }
}
