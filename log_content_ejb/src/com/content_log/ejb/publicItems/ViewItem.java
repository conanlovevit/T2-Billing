package com.content_log.ejb.publicItems;

import java.io.Serializable;

import java.util.Map;

public class ViewItem implements Serializable {
   private String strDate;
   private Map<String,String> dataMap;

    public ViewItem() {        
    }
    public ViewItem(String strDate, Map<String, String> dataMap) {
        super();
        this.strDate = strDate;
        this.dataMap = dataMap;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setDataMap(Map<String, String> dataMap) {
        this.dataMap = dataMap;
    }

    public Map<String, String> getDataMap() {
        return dataMap;
    }
}
