package com.viettel.billing_log.publicItem;

import java.io.Serializable;

public class CardDepositeItem implements Serializable {
    
    private int sumMoney;
    private String strDate;
    public CardDepositeItem() {
      
    }
    public CardDepositeItem(int sumMoney, String strDate) {
     
        this.sumMoney = sumMoney;
        this.strDate = strDate;
    }

    public void setSumMoney(int sumMoney) {
        this.sumMoney = sumMoney;
    }

    public int getSumMoney() {
        return sumMoney;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrDate() {
        return strDate;
    }
}
