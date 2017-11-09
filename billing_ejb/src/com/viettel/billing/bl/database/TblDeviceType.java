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
@NamedQueries({ @NamedQuery(name = "TblDeviceType.findAll", query = "select o from TblDeviceType o") })
@Table(name = "TBL_DEVICE_TYPE")
public class TblDeviceType {
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, unique = true, length = 20)
    private String name;
    @OneToMany(mappedBy = "tblDeviceType", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<TblDevice> tblDeviceList;

    public TblDeviceType() {
    }

    public TblDeviceType(Long id, String name) {
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

    public List<TblDevice> getTblDeviceList() {
        return tblDeviceList;
    }

    public void setTblDeviceList(List<TblDevice> tblDeviceList) {
        this.tblDeviceList = tblDeviceList;
    }

    public TblDevice addTblDevice(TblDevice tblDevice) {
        getTblDeviceList().add(tblDevice);
        tblDevice.setTblDeviceType(this);
        return tblDevice;
    }

    public TblDevice removeTblDevice(TblDevice tblDevice) {
        getTblDeviceList().remove(tblDevice);
        tblDevice.setTblDeviceType(null);
        return tblDevice;
    }
}
