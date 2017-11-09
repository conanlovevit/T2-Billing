package com.idc.launcher.items;

import java.io.Serializable;

import java.util.List;

public class ItemItem implements Serializable {
    private Long id;
    private ItemItem parent;
    private ItemType type;
    
    private String name;
    private String code;
    private String image;
    private String icon;
    private String url;
    private Integer pos;
    private String packageId;
    private Long deviceId;


    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setParent(ItemItem parent) {
        this.parent = parent;
    }

    public ItemItem getParent() {
        return parent;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageId() {
        return packageId;
    }
}
