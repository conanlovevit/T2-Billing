package com.idc.launcher.items;

import java.io.Serializable;

import java.sql.Timestamp;

public class ItemNotice implements Serializable {
    public static int TYPE_TEXT = 0;
    public static int TYPE_IMAGE = 1;
    
    private Long id;
    private int type;
    private String title;
    private String content;
    private String image;
    private Timestamp start_time;
    private Timestamp end_time;
    private Long frequency;
    private Long duration;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getDuration() {
        return duration;
    }
}

