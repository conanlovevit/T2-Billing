package com.idc.launcher.items;

import java.io.Serializable;

public class ItemDevice implements Serializable {
    private Long id;
    private String deviceIdStart;
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDeviceIdStart(String deviceIdStart) {
        this.deviceIdStart = deviceIdStart;
    }

    public String getDeviceIdStart() {
        return deviceIdStart;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
