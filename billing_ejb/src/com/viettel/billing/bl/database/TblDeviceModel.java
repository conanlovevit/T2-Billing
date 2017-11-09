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
@NamedQueries({ @NamedQuery(name = "TblDeviceModel.findAll", query = "select o from TblDeviceModel o"),
                })
@Table(name = "TBL_DEVICE_MODEL")
public class TblDeviceModel {
    @Column(nullable = false, length = 7)
    private String code;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(length = 2000)
    private String info;
    @Column(nullable = false, length = 50)
    private String name;
    @OneToMany(mappedBy = "tblDeviceModel", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<TblDevice> tblDeviceList;

    public TblDeviceModel() {
    }

    public TblDeviceModel(String code, Long id, String info, String name) {
        this.code = code;
        this.id = id;
        this.info = info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
        tblDevice.setTblDeviceModel(this);
        return tblDevice;
    }

    public TblDevice removeTblDevice(TblDevice tblDevice) {
        getTblDeviceList().remove(tblDevice);
        tblDevice.setTblDeviceModel(null);
        return tblDevice;
    }
}
