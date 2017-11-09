package com.content_log.ejb.bl.model;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Direction;
import org.eclipse.persistence.annotations.NamedStoredProcedureQueries;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

@Entity
@NamedQueries({ @NamedQuery(name = "TblGcm.findAll", query = "select o from TblGcm o"),
                @NamedQuery(name = "TblGcm.findActive", query = "select o from TblGcm o where o.lastUpdate >= :yesterday"),
                })
@NamedStoredProcedureQueries ({
    @NamedStoredProcedureQuery(  
        name="TblGcm.proc_gcm_create",  
        procedureName="proc_gcm_create",  
        returnsResultSet=false,  
        parameters={  
            @StoredProcedureParameter(queryParameter="iDeviceCode", name="iDeviceCode",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iGcmId", name="iGcmId",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iAppCode", name="iAppCode",direction=Direction.IN,type=Long.class),  
        }  
    ),
    @NamedStoredProcedureQuery(  
        name="TblGcm.proc_gcm_update",  
        procedureName="proc_gcm_update",  
        returnsResultSet=false,  
        parameters={  
            @StoredProcedureParameter(queryParameter="iDeviceCode", name="iDeviceCode",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iAppCode", name="iAppCode",direction=Direction.IN,type=Long.class),  
        }  
    ),
})
@Table(name = "TBL_GCM")
@Cacheable(false)
public class TblGcm implements Serializable {
    @Column(nullable = false)
    private Timestamp datetime;
    @Column(name = "DEVICE_CODE", nullable = false, unique = true, length = 100)
    private String deviceCode;
    @Column(name = "GCM_ID", nullable = false, length = 500)
    private String gcmId;
    @Id
    @Column(nullable = false)
    private Long id;
    @Column(name = "LAST_UPDATE", nullable = false)
    private Timestamp lastUpdate;
    @Column(name = "APP_CODE", nullable = false)
    private Long appCode;

    public TblGcm() {
    }

    public TblGcm(Timestamp datetime, String deviceCode, String gcmId, Long id, Timestamp lastUpdate) {
        this.datetime = datetime;
        this.deviceCode = deviceCode;
        this.gcmId = gcmId;
        this.id = id;
        this.lastUpdate = lastUpdate;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setAppCode(Long appCode) {
        this.appCode = appCode;
    }

    public Long getAppCode() {
        return appCode;
    }
}
