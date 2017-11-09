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
@NamedQueries({ @NamedQuery(name = "TblApp.findAll", query = "select o from TblApp o") })
@Table(name = "TBL_APP")
public class TblApp {
    @Column(name = "DEFAULT_PACKAGE", length = 200)
    private String defaultPackage;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    @OneToMany(mappedBy = "tblApp", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<TblAppItem> tblAppItemList;

    public TblApp() {
    }

    public TblApp(String defaultPackage, Long id, String name) {
        this.defaultPackage = defaultPackage;
        this.id = id;
        this.name = name;
    }

    public String getDefaultPackage() {
        return defaultPackage;
    }

    public void setDefaultPackage(String defaultPackage) {
        this.defaultPackage = defaultPackage;
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

    public List<TblAppItem> getTblAppItemList() {
        return tblAppItemList;
    }

    public void setTblAppItemList(List<TblAppItem> tblAppItemList) {
        this.tblAppItemList = tblAppItemList;
    }

    public TblAppItem addTblAppItem(TblAppItem tblAppItem) {
        getTblAppItemList().add(tblAppItem);
        tblAppItem.setTblApp(this);
        return tblAppItem;
    }

    public TblAppItem removeTblAppItem(TblAppItem tblAppItem) {
        getTblAppItemList().remove(tblAppItem);
        tblAppItem.setTblApp(null);
        return tblAppItem;
    }
}
