package com.idc.launcher.items;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class ItemLauncher implements Serializable {
    private Long id;
    private Long parentId;
    private String type;
    
    private String name;
    private String code;
    private String image;
    private String icon;
    private String url;
    private Integer pos;
    private String packageId;
    private Long lastUpdate;
    
    private List<ItemLauncher> childs;

    public ItemLauncher() {
        childs = new ArrayList<ItemLauncher>();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
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

    public void setPackageId(String packageName) {
        this.packageId = packageName;
    }

    public String getPackageId() {
        return packageId;
    }
    
    public void addChild(ItemLauncher child) {
        this.childs.add(child);
    }


    public List<ItemLauncher> getChilds() {
        return childs;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }
}
