package com.viettel.billing.publicItem;

import java.io.Serializable;

public class AccountInforItem implements Serializable {
    private String mobile;
    private String address;

    public void setMobile(String mobile) throws Exception {
        if (mobile != null && mobile.length() > 20) throw new Exception("Error: Số điện thoại có độ dài quá 20 kí tự!");
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setAddress(String address) throws Exception {
        if (address != null && address.length() > 400) throw new Exception("Error: địa chỉ có độ dài quá 400 kí tự!");
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
