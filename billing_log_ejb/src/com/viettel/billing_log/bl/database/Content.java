package com.viettel.billing_log.bl.database;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({ @NamedQuery(name = "Content.findAll", query = "select o from Content o"),
                @NamedQuery(name = "Content.findByContentId", query = "select o from Content o where LOWER(o.contentId) = LOWER(:content_id)"),
                @NamedQuery(name = "Content.search", query = "select o from Content o where LOWER(o.contentName) like :contentName AND o.contentType = :type"),
                })
public class Content {
    @Column(name = "CONTENT_ID", length = 50)
    private String contentId;
    @Column(name = "CONTENT_NAME", length = 200)
    private String contentName;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", 
                       sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
                    generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "CONTENT_TYPE_ID")
    private ContentType contentType;
    @ManyToOne
    @JoinColumn(name = "CP_ID")
    private Cp cp;

    public Content() {
    }

    public Content(String contentId, String contentName, ContentType contentType, Cp cp, Long id) {
        this.contentId = contentId;
        this.contentName = contentName;
        this.contentType = contentType;
        this.cp = cp;
        this.id = id;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
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

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Cp getCp() {
        return cp;
    }

    public void setCp(Cp cp) {
        this.cp = cp;
    }
}
