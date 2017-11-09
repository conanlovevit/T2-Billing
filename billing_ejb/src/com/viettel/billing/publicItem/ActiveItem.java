package com.viettel.billing.publicItem;

import java.io.Serializable;

public class ActiveItem implements Serializable {
    
    private int countActive;
    private String strDate;
    public ActiveItem() {
      
    }

    public ActiveItem(int countActive, String strDate) {
        this.countActive = countActive;
        this.strDate = strDate;
    }

    public void setCountActive(int countActive) {
        this.countActive = countActive;
    }

    public int getCountActive() {
        return countActive;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrDate() {
        return strDate;
    }

}
