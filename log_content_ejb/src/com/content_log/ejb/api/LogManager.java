package com.content_log.ejb.api;

import javax.ejb.Remote;

@Remote
public interface LogManager {
    void addLog(String deviceId, String deviceType, String deviceOs, 
                String location, 
                String service,String app, long contentId, String contentName, 
                long duraionSecond, long bandwidthKBytes, String speed, String streaming) throws Exception;
    
  
    void createGcm(String deviceId, String gcmId, long appCode);
    void updateGcm(String deviceId, long appCode);
}
