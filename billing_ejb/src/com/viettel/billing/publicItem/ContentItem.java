package com.viettel.billing.publicItem;

import java.io.Serializable;

public class ContentItem implements Serializable {
    private String serviceName;
    private Long contentId;
    private String contentName;
    private String contentImage;
    private Long price;
    private String description;
    
    private String cpId;
    private String cpName;

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getCpName() {
        return cpName;
    }
}
