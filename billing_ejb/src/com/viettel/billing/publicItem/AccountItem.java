package com.viettel.billing.publicItem;

import com.viettel.billing.publicItem.consts.AccountStatusDefines;

import java.io.Serializable;
import java.sql.Timestamp;

public class AccountItem implements Serializable {
    private Long id;
    
    private String accountId;
    private Integer status;
    
    private Timestamp creationDate;
    
    private String deviceId;
    private String deviceOs;
    private String deviceType;
    private String location;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setDeviceOs(String deviceOs) {
        this.deviceOs = deviceOs;
    }

    public String getDeviceOs() {
        return deviceOs;
    }
}
