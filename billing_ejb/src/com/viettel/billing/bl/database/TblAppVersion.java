package com.viettel.billing.bl.database;

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
@NamedQueries({ @NamedQuery(name = "TblAppVersion.findAll", query = "select o from TblAppVersion o") })
@Table(name = "TBL_APP_VERSION")
public class TblAppVersion {
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(name = "VERSION_CODE", nullable = false, unique = true, length = 100)
    private String versionCode;
    @OneToMany(mappedBy = "tblAppVersion", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<TblAppItem> tblAppItemList;

    public TblAppVersion() {
    }

    public TblAppVersion(Long id, String versionCode) {
        this.id = id;
        this.versionCode = versionCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public List<TblAppItem> getTblAppItemList() {
        return tblAppItemList;
    }

    public void setTblAppItemList(List<TblAppItem> tblAppItemList) {
        this.tblAppItemList = tblAppItemList;
    }

    public TblAppItem addTblAppItem(TblAppItem tblAppItem) {
        getTblAppItemList().add(tblAppItem);
        tblAppItem.setTblAppVersion(this);
        return tblAppItem;
    }

    public TblAppItem removeTblAppItem(TblAppItem tblAppItem) {
        getTblAppItemList().remove(tblAppItem);
        tblAppItem.setTblAppVersion(null);
        return tblAppItem;
    }
}
