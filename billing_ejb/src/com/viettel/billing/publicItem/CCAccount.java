package com.viettel.billing.publicItem;

import java.io.Serializable;

import java.util.Date;

public class CCAccount implements Serializable {
    private String device;
    private Date guaranteeExpired;
    private boolean active;
    
    private long id;
    private String accountId;
    private Date activeDate;
    private boolean block;
    private String source;
    private Date vipExpired;
    private String mac;
    
    public void setDevice(String device) {
        this.device = device;
    }

    public String getDevice() {
        return device;
    }

    public void setGuaranteeExpired(Date guaranteeExpired) {
        this.guaranteeExpired = guaranteeExpired;
    }

    public Date getGuaranteeExpired() {
        return guaranteeExpired;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public boolean isBlock() {
        return block;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setVipExpired(Date vipExpired) {
        this.vipExpired = vipExpired;
    }

    public Date getVipExpired() {
        return vipExpired;
    }


    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMac() {
        return mac;
    }
}
