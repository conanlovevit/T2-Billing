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
@NamedQueries({ @NamedQuery(name = "TblLocation.findAll", query = "select o from TblLocation o") })
@Table(name = "TBL_LOCATION")
public class TblLocation {
    @Column(nullable = false, unique = true, length = 20)
    private String code;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    @OneToMany(mappedBy = "tblLocation", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<TblAccount> tblAccountList;

    public TblLocation() {
    }

    public TblLocation(String code, Long id, String name) {
        this.code = code;
        this.id = id;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<TblAccount> getTblAccountList() {
        return tblAccountList;
    }

    public void setTblAccountList(List<TblAccount> tblAccountList) {
        this.tblAccountList = tblAccountList;
    }

    public TblAccount addTblAccount(TblAccount tblAccount) {
        getTblAccountList().add(tblAccount);
        tblAccount.setTblLocation(this);
        return tblAccount;
    }

    public TblAccount removeTblAccount(TblAccount tblAccount) {
        getTblAccountList().remove(tblAccount);
        tblAccount.setTblLocation(null);
        return tblAccount;
    }
}
