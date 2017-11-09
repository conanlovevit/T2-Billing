package com.content_log.ejb.bl.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "TblDevice.findAll", query = "select o from TblDevice o") })
@Table(name = "TBL_DEVICE")
public class TblDevice {
    @Column(name = "DEVICE_ID", nullable = false, unique = true, length = 100)
    private String deviceId;
    @Column(name = "DEVICE_TYPE", length = 20)
    private String deviceType;
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TblDevice_Id_Seq_Gen")
    private BigDecimal id;
    @Column(length = 100)
    private String os;

    public TblDevice() {
    }

    public TblDevice(String deviceId, String deviceType, BigDecimal id, String os) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.id = id;
        this.os = os;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public BigDecimal getId() {
        return id;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
