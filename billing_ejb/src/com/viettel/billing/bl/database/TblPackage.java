package com.viettel.billing.bl.database;

import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Direction;
import org.eclipse.persistence.annotations.NamedStoredProcedureQueries;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

@Entity
@NamedQueries({ @NamedQuery(name = "TblPackage.findAll", query = "select o from TblPackage o order by o.duration"),
                @NamedQuery(name = "TblPackage.getActive", query = "select o from TblPackage o where o.status = 1 order by o.duration"),
                @NamedQuery(name = "TblPackage.getBlock", query = "select o from TblPackage o where o.status = 0 order by o.duration"),
                })
@NamedStoredProcedureQueries ({
    @NamedStoredProcedureQuery(  
        name="TblDevice.ppm_check",  
        procedureName="ppm_check",  
        returnsResultSet=false,  
        parameters={  
            @StoredProcedureParameter(queryParameter="iAccountId", name="iAccountId",direction=Direction.IN,type=Long.class),  
            @StoredProcedureParameter(queryParameter="iPackageId", name="iPackageId",direction=Direction.IN,type=Long.class),  
            @StoredProcedureParameter(queryParameter="oStatus", name="oStatus",direction=Direction.OUT,type=String.class),  
            @StoredProcedureParameter(queryParameter="oNeedPayment", name="oNeedPayment",direction=Direction.OUT,type=Integer.class),  
            @StoredProcedureParameter(queryParameter="oPackageName", name="oPackageName",direction=Direction.OUT,type=String.class),  
            @StoredProcedureParameter(queryParameter="oPrice", name="oPrice",direction=Direction.OUT,type=Long.class)  
        }  
    ),
    @NamedStoredProcedureQuery(  
        name="TblDevice.ppm",  
        procedureName="ppm",  
        returnsResultSet=false,  
        parameters={  
            @StoredProcedureParameter(queryParameter="iAccountId", name="iAccountId",direction=Direction.IN,type=Long.class),  
            @StoredProcedureParameter(queryParameter="iPackageId", name="iPackageId",direction=Direction.IN,type=Long.class),  
            @StoredProcedureParameter(queryParameter="oStatus", name="oStatus",direction=Direction.OUT,type=String.class),  
            @StoredProcedureParameter(queryParameter="oExpired", name="oExpired",direction=Direction.OUT,type=Timestamp.class),  
            @StoredProcedureParameter(queryParameter="oAutoRebuy", name="oAutoRebuy",direction=Direction.OUT,type=Long.class)  
        }  
    ),
    @NamedStoredProcedureQuery(  
        name="TblDevice.ppm_remove",  
        procedureName="ppm_remove",  
        returnsResultSet=false,  
        parameters={  
            @StoredProcedureParameter(queryParameter="iAccountId", name="iAccountId",direction=Direction.IN,type=Long.class),  
            @StoredProcedureParameter(queryParameter="iPackageAccountId", name="iPackageAccountId",direction=Direction.IN,type=Long.class),  
            @StoredProcedureParameter(queryParameter="oStatus", name="oStatus",direction=Direction.OUT,type=String.class),  
        }  
    ),
})
@Table(name = "TBL_PACKAGE")
@Cacheable(false)
public class TblPackage {
    @Column(name = "AUTO_REBUY")
    private Integer autoRebuy;
    @Column(length = 200)
    private String description;
    @Column(nullable = false)
    private Integer duration;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    private Long price;
    @Column(nullable = false)
    private Integer status;

    public TblPackage() {
    }

    public TblPackage(Integer autoRebuy, String description, Integer duration, Long id, String name, Long price,
                      Integer status) {
        this.autoRebuy = autoRebuy;
        this.description = description;
        this.duration = duration;
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public Integer getAutoRebuy() {
        return autoRebuy;
    }

    public void setAutoRebuy(Integer autoRebuy) {
        this.autoRebuy = autoRebuy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
