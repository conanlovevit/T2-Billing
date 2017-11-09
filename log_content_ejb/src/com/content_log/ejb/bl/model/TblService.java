package com.content_log.ejb.bl.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "TblService.findAll", query = "select o from TblService o") })
@Table(name = "TBL_SERVICE")
public class TblService {
    @Id
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    @OneToMany(mappedBy = "tblService", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<TblContent> tblContentList;

    public TblService() {
    }

    public TblService(Long id, String name) {
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

    public List<TblContent> getTblContentList() {
        return tblContentList;
    }

    public void setTblContentList(List<TblContent> tblContentList) {
        this.tblContentList = tblContentList;
    }

    public TblContent addTblContent(TblContent tblContent) {
        getTblContentList().add(tblContent);
        tblContent.setTblService(this);
        return tblContent;
    }

    public TblContent removeTblContent(TblContent tblContent) {
        getTblContentList().remove(tblContent);
        tblContent.setTblService(null);
        return tblContent;
    }
}
