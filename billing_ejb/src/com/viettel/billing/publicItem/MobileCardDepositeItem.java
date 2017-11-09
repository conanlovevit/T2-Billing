package com.viettel.billing.publicItem;

import java.io.Serializable;

public class MobileCardDepositeItem implements Serializable {
    public final static String TYPE_VIETTEL         = "VTEL";
    public final static String TYPE_VINAPHONE       = "GPC";
    public final static String TYPE_MOBIFONE        = "VMS";
    
    private String serial;
    private String code;
    private String cardType;
    private long money;
    private boolean verify;

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getSerial() {
        return serial;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public long getMoney() {
        return money;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public boolean isVerify() {
        return verify;
    }
}
