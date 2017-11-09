package com.idc.launcher.database;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({ @NamedQuery(name = "Notice.findAll", query = "select o from Notice o order by o.id desc"),
                @NamedQuery(name = "Notice.findActive", query = "select o from Notice o where o.startTime < CURRENT_TIMESTAMP and o.endTime > CURRENT_TIMESTAMP")
                })
public class Notice {
    @Column(length = 2000)
    private String content;
    @Column(name = "CREATE_TIME", nullable = false)
    private Timestamp createTime;
    @Column(name = "END_TIME", nullable = false)
    private Timestamp endTime;
    @Column(nullable = false)
    private Long frequency;
    @Column(nullable = false)
    private Long duration;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", 
                       sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
                    generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 50)
    private String image;
    @Column(name = "START_TIME", nullable = false)
    private Timestamp startTime;
    @Column(length = 100)
    private String title;
    @ManyToOne
    @JoinColumn(name = "TYPE_ID")
    private NoticeType noticeType;

    public Notice() {
    }

    public Notice(String content, Timestamp createTime, Timestamp endTime, Long frequency, Long id, String image,
                  Timestamp startTime, String title, NoticeType noticeType) {
        this.content = content;
        this.createTime = createTime;
        this.endTime = endTime;
        this.frequency = frequency;
        this.id = id;
        this.image = image;
        this.startTime = startTime;
        this.title = title;
        this.noticeType = noticeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }


    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getDuration() {
        return duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public NoticeType getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(NoticeType noticeType) {
        this.noticeType = noticeType;
    }
}
