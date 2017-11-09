package com.viettel.billing.bl.database;

import java.sql.Timestamp;

import javax.persistence.Cacheable;
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
@NamedQueries({ @NamedQuery(name = "TblAccount.findAll", query = "select o from TblAccount o") })
@Table(name = "TBL_ACCOUNT")
@Cacheable(false)
public class TblAccount {
    @Column(name = "ACCOUNT_ID", nullable = false, unique = true, length = 100)
    private String accountId;
    @Column(length = 400)
    private String address;
    @Column(name = "CREATION_DATE", nullable = false)
    private Timestamp creationDate;
    @Column(name = "VIP_EXPIRED")
    private Timestamp vipExpired;
    @Column(name = "DEVICE_REGISTER_ID")
    private Long deviceRegisterId;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(length = 20)
    private String mobile;
    @Column(length = 20)
    private String source;
    @Column(nullable = false)
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private TblLocation tblLocation;

    public TblAccount() {
    }

    public TblAccount(String accountId, String address, Timestamp creationDate, Long deviceRegisterId, Long id,
                      TblLocation tblLocation, String mobile, String source, Integer status) {
        this.accountId = accountId;
        this.address = address;
        this.creationDate = creationDate;
        this.deviceRegisterId = deviceRegisterId;
        this.id = id;
        this.tblLocation = tblLocation;
        this.mobile = mobile;
        this.source = source;
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Long getDeviceRegisterId() {
        return deviceRegisterId;
    }

    public void setDeviceRegisterId(Long deviceRegisterId) {
        this.deviceRegisterId = deviceRegisterId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public TblLocation getTblLocation() {
        return tblLocation;
    }

    public void setTblLocation(TblLocation tblLocation) {
        this.tblLocation = tblLocation;
    }

    public void setVipExpired(Timestamp vipExpired) {
        this.vipExpired = vipExpired;
    }

    public Timestamp getVipExpired() {
        return vipExpired;
    }
}
