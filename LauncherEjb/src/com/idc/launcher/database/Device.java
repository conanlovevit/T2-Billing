package com.idc.launcher.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "Device.findAll", query = "select o from Device o order by LENGTH(o.deviceIdStart) desc") })
public class Device {
    @Column(name = "DEVICE_ID_START", length = 20)
    private String deviceIdStart;
    @Id
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;

    public Device() {
    }

    public Device(String deviceIdStart, Long id, String name) {
        this.deviceIdStart = deviceIdStart;
        this.id = id;
        this.name = name;
    }

    public String getDeviceIdStart() {
        return deviceIdStart;
    }

    public void setDeviceIdStart(String deviceIdStart) {
        this.deviceIdStart = deviceIdStart;
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
}
