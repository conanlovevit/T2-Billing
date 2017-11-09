package com.content_log.ejb.bl.model;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "TblContent.findAll", query = "select o from TblContent o") })
@Table(name = "TBL_CONTENT")
public class TblContent {
    @Column(name = "CONTENT_ID", nullable = false, unique = true)
    private BigDecimal contentId;
    @Column(name = "CONTENT_NAME", length = 200)
    private String contentName;
    @Id
    @Column(nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "SERVICE_ID")
    private TblService tblService;

    public TblContent() {
    }

    public TblContent(BigDecimal contentId, String contentName, Long id, TblService tblService) {
        this.contentId = contentId;
        this.contentName = contentName;
        this.id = id;
        this.tblService = tblService;
    }

    public BigDecimal getContentId() {
        return contentId;
    }

    public void setContentId(BigDecimal contentId) {
        this.contentId = contentId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public TblService getTblService() {
        return tblService;
    }

    public void setTblService(TblService tblService) {
        this.tblService = tblService;
    }
}
