package com.viettel.billing_log.publicItem;

import java.io.Serializable;

public class ContentItem implements Serializable {
    private Long id;
    private String contentName;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentName() {
        return contentName;
    }
}
