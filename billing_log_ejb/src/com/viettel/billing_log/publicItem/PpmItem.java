package com.viettel.billing_log.publicItem;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class PpmItem {
    private String accId;
    private String accAccId;
    private String contentId;
    private String contentName;
    private long price;
    private long creationDate;
    private long expired;
    private boolean rebuy = false;

    public PpmItem(String accId, String accAccId, String contentId, String contentName, long price, long creationDate, long expired) {
        this.accId = accId;     
        this.accAccId = accAccId;     
        this.contentId = contentId;     
        this.contentName = contentName;     
        this.price = price;     
        this.creationDate = creationDate;     
        this.expired = expired;     
    }

    public PpmItem(String accId, String accAccId, String contentId, String contentName, long price, long creationDate, long expired, boolean isRebuy) {
        this.accId = accId;     
        this.accAccId = accAccId;     
        this.contentId = contentId;     
        this.contentName = contentName;     
        this.price = price;     
        this.creationDate = creationDate;     
        this.expired = expired;   
        this.rebuy = isRebuy;
    }

    public PpmItem(String str) {
        JSONObject lret = (JSONObject) JSONValue.parse(str);
        accId = (String) lret.get("aId");
        accAccId = (String) lret.get("aAccId");
        contentId = (String) lret.get("cId");
        contentName = (String) lret.get("cName");
        price = (Long) lret.get("price");
        creationDate = (Long) lret.get("create");
        expired = (Long) lret.get("expired");
        rebuy = (Boolean) lret.get("rebuy");
    }

    public String toString() {
        JSONObject lret = new JSONObject();
        lret.put("aId", accId);
        lret.put("aAccId", accAccId);
        lret.put("cId", contentId);
        lret.put("cName", contentName);
        lret.put("price", price);
        lret.put("create", creationDate);
        lret.put("expired", expired);
        lret.put("rebuy", rebuy);
        return lret.toString();
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccAccId(String accAccId) {
        this.accAccId = accAccId;
    }

    public String getAccAccId() {
        return accAccId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentName() {
        return contentName;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getPrice() {
        return price;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setExpired(long expired) {
        this.expired = expired;
    }

    public long getExpired() {
        return expired;
    }

    public void setRebuy(boolean rebuy) {
        this.rebuy = rebuy;
    }

    public boolean isRebuy() {
        return rebuy;
    }
}
