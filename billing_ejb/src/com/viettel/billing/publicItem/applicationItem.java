package com.viettel.billing.publicItem;

import java.io.Serializable;

public class applicationItem implements Serializable {
    private String name;
    private String packageName;
    private String image;
    
    public applicationItem(Object[] lst, String imageRoot) {
        this.name = (String) lst[0];
        this.image = lst[1] != null ? imageRoot + (String)lst[1] : "";
        this.packageName = (String) lst[2];
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
