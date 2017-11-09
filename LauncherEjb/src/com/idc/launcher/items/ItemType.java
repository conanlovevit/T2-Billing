package com.idc.launcher.items;

import java.io.Serializable;

public class ItemType implements Serializable {
    private Long id;
    private String name;

    public ItemType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
