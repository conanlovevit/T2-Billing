package com.viettel.billing.bl.database;

import java.math.BigDecimal;

import java.sql.Timestamp;

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

import org.eclipse.persistence.annotations.Direction;
import org.eclipse.persistence.annotations.NamedStoredProcedureQueries;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

@Entity
@NamedQueries({ @NamedQuery(name = "TblDevice.findAll", query = "select o from TblDevice o"),
                @NamedQuery(name = "TblDevice.findByCode", query = "select o from TblDevice o where LOWER(o.code) = LOWER(:device_code)"),
                })
@NamedStoredProcedureQueries ({
    @NamedStoredProcedureQuery(  
        name="TblDevice.register",  
        procedureName="register",  
        returnsResultSet=false,  
        parameters={  
            @StoredProcedureParameter(queryParameter="iDeviceCode", name="iDeviceCode",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iSign", name="iSign",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="oStatus", name="oStatus",direction=Direction.OUT,type=String.class),  
            @StoredProcedureParameter(queryParameter="oId", name="oId",direction=Direction.OUT,type=Long.class),  
            @StoredProcedureParameter(queryParameter="oAccountId", name="oAccountId",direction=Direction.OUT,type=String.class),  
            @StoredProcedureParameter(queryParameter="oNeedCreateAccount", name="oNeedCreateAccount",direction=Direction.OUT,type=Long.class)  
        }  
    ),
        @NamedStoredProcedureQuery(  
        name="TblDevice.import_z5",  
        procedureName="IMPORTZ5",  
        returnsResultSet=false,  
        parameters={  
            @StoredProcedureParameter(queryParameter="iDeviceModelCode", name="iDeviceModelCode",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iDeviceCode", name="iDeviceCode",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iAccountName", name="iAccountName",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iActiveDate", name="iActiveDate",direction=Direction.IN,type=String.class), 
            @StoredProcedureParameter(queryParameter="iExpireDate", name="iExpireDate",direction=Direction.IN,type=String.class), 
            @StoredProcedureParameter(queryParameter="iAccountId", name="iAccountId",direction=Direction.IN,type=String.class),
            @StoredProcedureParameter(queryParameter="oStatus", name="oStatus",direction=Direction.OUT,type=String.class),
            @StoredProcedureParameter(queryParameter="oDesc", name="oDesc",direction=Direction.OUT,type=String.class)
        }  
    ),
    @NamedStoredProcedureQuery(  
        name="TblDevice.create_device",  
        procedureName="create_device",  
        returnsResultSet=false,  
        parameters={  
            @StoredProcedureParameter(queryParameter="iDeviceModelCode", name="iDeviceModelCode",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iDeviceCode", name="iDeviceCode",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iMac1", name="iMac1",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iMac2", name="iMac2",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="oStatus", name="oStatus",direction=Direction.OUT,type=String.class)
        }  
    ),
    @NamedStoredProcedureQuery(  
        name="TblDevice.login",  
        procedureName="login",  
        returnsResultSet=false,  
        parameters={  
            @StoredProcedureParameter(queryParameter="iDeviceCode", name="iDeviceCode",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iTime", name="iTime",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iSign", name="iSign",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="oStatus", name="oStatus",direction=Direction.OUT,type=String.class),
            @StoredProcedureParameter(queryParameter="oId", name="oId",direction=Direction.OUT,type=Long.class),  
            @StoredProcedureParameter(queryParameter="oAccountId", name="oAccountId",direction=Direction.OUT,type=String.class),  
            @StoredProcedureParameter(queryParameter="oDeviceType", name="oDeviceType",direction=Direction.OUT,type=String.class),  
            @StoredProcedureParameter(queryParameter="oDeviceOs", name="oDeviceOs",direction=Direction.OUT,type=String.class),  
            @StoredProcedureParameter(queryParameter="oLocation", name="oLocation",direction=Direction.OUT,type=String.class)  
        }  
    )
})
@Table(name = "TBL_DEVICE")
public class TblDevice {
    @Column(nullable = false, length = 100)
    private String code;
    @Column(name = "CREATION_ACCOUNT_ID")
    private Long creationAccountId;
    @Column(nullable = false)
    private Timestamp datetime;
    @Column(name = "GUARANTEE_EXPIRED")
    private Timestamp guaranteeExpired;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(name = "OS_INFO", length = 20)
    private String osInfo;
    @Column(nullable = false)
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "TYPE_ID")
    private TblDeviceType tblDeviceType;
    @ManyToOne
    @JoinColumn(name = "DEVICE_MODEL_ID")
    private TblDeviceModel tblDeviceModel;
    @ManyToOne
    @JoinColumn(name = "OS_ID")
    private TblDeviceOs tblDeviceOs;
    @Column(length = 50)
    private String mac1;
    @Column(length = 50)
    private String mac2;

    public TblDevice() {
    }

    public TblDevice(String code, Long creationAccountId, Timestamp datetime, TblDeviceModel tblDeviceModel,
                     Timestamp guaranteeExpired, Long id, TblDeviceOs tblDeviceOs, String osInfo, Integer status,
                     TblDeviceType tblDeviceType) {
        this.code = code;
        this.creationAccountId = creationAccountId;
        this.datetime = datetime;
        this.tblDeviceModel = tblDeviceModel;
        this.guaranteeExpired = guaranteeExpired;
        this.id = id;
        this.tblDeviceOs = tblDeviceOs;
        this.osInfo = osInfo;
        this.status = status;
        this.tblDeviceType = tblDeviceType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCreationAccountId() {
        return creationAccountId;
    }

    public void setCreationAccountId(Long creationAccountId) {
        this.creationAccountId = creationAccountId;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }


    public Timestamp getGuaranteeExpired() {
        return guaranteeExpired;
    }

    public void setGuaranteeExpired(Timestamp guaranteeExpired) {
        this.guaranteeExpired = guaranteeExpired;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getOsInfo() {
        return osInfo;
    }

    public void setOsInfo(String osInfo) {
        this.osInfo = osInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public TblDeviceType getTblDeviceType() {
        return tblDeviceType;
    }

    public void setTblDeviceType(TblDeviceType tblDeviceType) {
        this.tblDeviceType = tblDeviceType;
    }

    public TblDeviceModel getTblDeviceModel() {
        return tblDeviceModel;
    }

    public void setTblDeviceModel(TblDeviceModel tblDeviceModel) {
        this.tblDeviceModel = tblDeviceModel;
    }

    public TblDeviceOs getTblDeviceOs() {
        return tblDeviceOs;
    }

    public void setTblDeviceOs(TblDeviceOs tblDeviceOs) {
        this.tblDeviceOs = tblDeviceOs;
    }

    public void setMac1(String mac1) {
        this.mac1 = mac1;
    }

    public String getMac1() {
        return mac1;
    }

    public void setMac2(String mac2) {
        this.mac2 = mac2;
    }

    public String getMac2() {
        return mac2;
    }
}
