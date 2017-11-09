package com.content_log.ejb.bl.model;

import java.sql.Timestamp;

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
@NamedQueries({ @NamedQuery(name = "TblLog.findAll", query = "select o from TblLog o") })
@NamedStoredProcedureQueries ({
    @NamedStoredProcedureQuery(  
        name="TblLog.proc_insert_log",  
        procedureName="proc_insert_log",  
        returnsResultSet=false,  
        parameters={  
            @StoredProcedureParameter(queryParameter="iDeviceId", name="iDeviceId",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iDeviceType", name="iDeviceType",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iDeviceOs", name="iDeviceOs",direction=Direction.IN,type=String.class),  

            @StoredProcedureParameter(queryParameter="iLocation", name="iLocation",direction=Direction.IN,type=String.class),  

            @StoredProcedureParameter(queryParameter="iService", name="iService",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iApp", name="iApp",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iContentId", name="iContentId",direction=Direction.IN,type=Long.class),  
            @StoredProcedureParameter(queryParameter="iContentName", name="iContentName",direction=Direction.IN,type=String.class),  

            @StoredProcedureParameter(queryParameter="iDuration", name="iDuration",direction=Direction.IN,type=Long.class),  
            @StoredProcedureParameter(queryParameter="iToken", name="iToken",direction=Direction.IN,type=String.class),  

            @StoredProcedureParameter(queryParameter="iBandwidth", name="iBandwidth",direction=Direction.IN,type=Long.class),  
            @StoredProcedureParameter(queryParameter="iSpeed", name="iSpeed",direction=Direction.IN,type=String.class),  
            @StoredProcedureParameter(queryParameter="iStreaming", name="iStreaming",direction=Direction.IN,type=String.class),  
        }  
    ),
})
@Table(name = "TBL_LOG")
public class TblLog {
    @Column(name = "CONTENT_ID", nullable = false)
    private Long contentId;
    @Column(name = "APP_ID", nullable = false)
    private Long appId;
    @Column(name = "CREATE_DATETIME", nullable = false)
    private Timestamp createDatetime;
    @Column(name = "DEVICE_ID", nullable = false)
    private Long deviceId;
    private Long duration;
    @Id
    @Column(nullable = false)
    private Long id;
    @Column(length = 100)
    private String location;
    @Column(length = 2000)
    private String token;

    public TblLog() {
    }

    public TblLog(Long contentId, Timestamp createDatetime, Long deviceId, Long duration, Long id, String location,
                  String token) {
        this.contentId = contentId;
        this.createDatetime = createDatetime;
        this.deviceId = deviceId;
        this.duration = duration;
        this.id = id;
        this.location = location;
        this.token = token;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }
    public Timestamp getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Timestamp createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
