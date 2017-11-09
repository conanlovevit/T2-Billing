package com.lbv4.bl.cdn;

public interface CdnInterface {
    public String getStream(String ip, String path) throws Exception;
}
