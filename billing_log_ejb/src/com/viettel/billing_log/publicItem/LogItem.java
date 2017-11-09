package com.viettel.billing_log.publicItem;

import java.io.Serializable;

public class LogItem implements Serializable {
    private String action;
    private String contentType;
    private String content;
    private long money;
    private long creationDate;
    private long expiredDate;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public long getMoney() {
        return money;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setExpiredDate(long expiredDate) {
        this.expiredDate = expiredDate;
    }

    public long getExpiredDate() {
        return expiredDate;
    }
}
