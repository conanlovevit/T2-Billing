package com.content_log.ejb.publicItems;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class LogItem {
    private String token;
    
    private String service;
    private String app;
    private long contentId;
    private String contentName;
    
    private long duration;
    private long bandwidth;
    private String speed;
    private String streaming;
    
    public LogItem(String token, 
                   String service, String app,long contentId, String contentName, 
                   long duration, long bandwidth, String speed, String streaming) {
        setToken(token);
        setService(service);
        setApp(app);
        setContentId(contentId);
        setContentName(contentName);
        setDuration(duration);
        setBandwidth(bandwidth);
        setSpeed(speed);
        setStreaming(streaming);
    }

    public LogItem(String str) {
        JSONObject lret = (JSONObject) JSONValue.parse(str);
        setToken((String) lret.get("t"));
        
        setService((String) lret.get("s"));
        setApp((String) lret.get("a"));
        setContentId((Long)lret.get("cI"));
        setContentName((String) lret.get("cN"));
        
        setDuration((Long)lret.get("d"));
        setBandwidth((Long)lret.get("b"));
        setSpeed((String) lret.get("sp"));
        setStreaming((String) lret.get("st"));
    }

    public String toString() {
        JSONObject lret = new JSONObject();
        lret.put("t", token);
        
        lret.put("s", service);
        lret.put("a", app);
        lret.put("cI", contentId);
        lret.put("cN", contentName);
        
        lret.put("d", duration);
        lret.put("b", bandwidth);
        lret.put("sp", speed);
        lret.put("st", streaming);
        return lret.toString();
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getApp() {
        return app;
    }
    
    public void setService(String service) {
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentName() {
        return contentName;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public void setBandwidth(long bandwidth) {
        this.bandwidth = bandwidth;
    }

    public long getBandwidth() {
        return bandwidth;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getSpeed() {
        return speed;
    }

    public void setStreaming(String streaming) {
        this.streaming = streaming;
    }

    public String getStreaming() {
        return streaming;
    }
}
