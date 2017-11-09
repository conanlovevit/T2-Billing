package com.idc.launcher.items;

import java.io.Serializable;

public class ItemConfig implements Serializable {
    private String name;
    private String value;

    public ItemConfig(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
