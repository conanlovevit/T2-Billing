package com.lbv4.api;

import javax.ejb.Remote;

@Remote
public interface LoadBalanceApi {
    String getStreamingUrl(String ip, String path, String cdnName) throws Exception;
}
