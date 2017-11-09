package com.idc.launcher.database;

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
@NamedQueries({ @NamedQuery(name = "PackageIcon.findAll", query = "select o from PackageIcon o") })
@Table(name = "PACKAGE_ICON")
public class PackageIcon {
    @Column(name = "APP_NAME", nullable = false, length = 50)
    private String appName;
    @Column(nullable = false, length = 50)
    private String icon;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", 
                       sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
                    generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(name = "PACKAGE_NAME", nullable = false, length = 100)
    private String packageName;

    public PackageIcon() {
    }

    public PackageIcon(String appName, String icon, Long id, String packageName) {
        this.appName = appName;
        this.icon = icon;
        this.id = id;
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
