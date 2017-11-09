package com.viettel.billing.bl.database;

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
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "TblAppItem.findAll", query = "select o from TblAppItem o") })
@Table(name = "TBL_APP_ITEM")
public class TblAppItem {
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 200)
    private String image;
    @Column(name = "PACKAGE", nullable = false, length = 200)
    private String package_;
    @ManyToOne
    @JoinColumn(name = "APP_ID")
    private TblApp tblApp;
    @ManyToOne
    @JoinColumn(name = "VERSION_ID")
    private TblAppVersion tblAppVersion;

    public TblAppItem() {
    }

    public TblAppItem(TblApp tblApp, Long id, String image, String package_, TblAppVersion tblAppVersion) {
        this.tblApp = tblApp;
        this.id = id;
        this.image = image;
        this.package_ = package_;
        this.tblAppVersion = tblAppVersion;
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

    public String getPackage_() {
        return package_;
    }

    public void setPackage_(String package_) {
        this.package_ = package_;
    }


    public TblApp getTblApp() {
        return tblApp;
    }

    public void setTblApp(TblApp tblApp) {
        this.tblApp = tblApp;
    }

    public TblAppVersion getTblAppVersion() {
        return tblAppVersion;
    }

    public void setTblAppVersion(TblAppVersion tblAppVersion) {
        this.tblAppVersion = tblAppVersion;
    }
}
