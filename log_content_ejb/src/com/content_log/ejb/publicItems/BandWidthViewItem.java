package com.content_log.ejb.publicItems;

import java.io.Serializable;

public class BandWidthViewItem implements Serializable {
    
    private long bandwidth;
    private String month;
    public BandWidthViewItem() {
     
    }

    public BandWidthViewItem(String month,long bandwidth) {       
        this.bandwidth = bandwidth;
        this.month = month;
    }

    public void setBandwidth(long bandwidth) {
        this.bandwidth = bandwidth;
    }

    public long getBandwidth() {
        return bandwidth;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }
}
