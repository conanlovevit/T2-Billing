package com.ztv.oauth2;

import java.util.ArrayList;
import java.util.List;

public class AccountInfo {
    private String id               = "";
    private String accountId          = "";
    private String deviceId         = "";
    
    // add deviceType, os
    private String deviceType       = "";
    private String os               = "";
    
    // add location
    private String locationCode     = "";
    
    private List<String> scopes     = null;

    public AccountInfo(String id, String accountId, String deviceId, String deviceType, String os, String locationCode) {
        setId(id);
        setAccountId(accountId);
        setDeviceId(deviceId);
        setDeviceType(deviceType);
        setOs(os);
        setLocationCode(locationCode);
    }

    public void setId(String id) {
        this.id = (id == null ? "" : id);
    }

    public String getId() {
        return id;
    }

    public void setAccountId(String account) {
        this.accountId = (account == null ? "" : account);
    }

    public String getAccountId() {
        return accountId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs() {
        return os;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public List<String> getScopes() {
        return scopes;
    }
}
