package com.viettel.billing.publicItem;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.util.List;

public class PackageItem implements Serializable {
    private Long id;
    private String name;
    
    private Long price;
    private Integer duration;

    private boolean active;
    private boolean autoRebuy;
    
    private String description;
    
    // current status
    private boolean used;
    private Long packageAccountId;
    private Timestamp expired;

//a.id, a.package_id, a.expired_date, a.auto_rebuy, a.name, a.description

    public static PackageItem parseFromViewActivePackageAccount(Object[] lst) {
        PackageItem ret = new PackageItem();
        ret.setUsed(true);
        ret.setPackageAccountId(((BigDecimal) lst[0]).longValue());
        ret.setExpired((Timestamp) lst[2]);
        ret.setAutoRebuy(((BigDecimal) lst[3]).longValue());
        
        ret.setId(((BigDecimal) lst[1]).longValue());
        ret.setName((String) lst[4]);
        ret.setDescription((String) lst[5]);
        ret.setPrice(((BigDecimal) lst[6]).longValue());
        
        ret.setDuration(((BigDecimal) lst[7]).intValue());
        
        return ret;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setActive(long active) {
        this.active = active == 1;
    }

    public boolean isActive() {
        return active;
    }

    public void setAutoRebuy(boolean autoRebuy) {
        this.autoRebuy = autoRebuy;
    }

    public void setAutoRebuy(long autoRebuy) {
        this.autoRebuy = autoRebuy == 1;
    }

    public boolean isAutoRebuy() {
        return autoRebuy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }

    public void setPackageAccountId(Long packageAccountId) {
        this.packageAccountId = packageAccountId;
    }

    public Long getPackageAccountId() {
        return packageAccountId;
    }

    public void setExpired(Timestamp expired) {
        this.expired = expired;
    }

    public Timestamp getExpired() {
        return expired;
    }
}
