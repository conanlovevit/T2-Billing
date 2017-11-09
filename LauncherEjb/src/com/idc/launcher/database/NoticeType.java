package com.idc.launcher.database;

import java.io.Serializable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "NoticeType.findAll", query = "select o from NoticeType o") })
@Table(name = "NOTICE_TYPE")
public class NoticeType {
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", 
                       sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
                    generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    @OneToMany(mappedBy = "noticeType", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Notice> noticeList;

    public NoticeType() {
    }

    public NoticeType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Notice> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<Notice> noticeList) {
        this.noticeList = noticeList;
    }

    public Notice addNotice(Notice notice) {
        getNoticeList().add(notice);
        notice.setNoticeType(this);
        return notice;
    }

    public Notice removeNotice(Notice notice) {
        getNoticeList().remove(notice);
        notice.setNoticeType(null);
        return notice;
    }
}
