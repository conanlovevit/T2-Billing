package com.idc.launcher.database;

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

@Entity
@NamedQueries({ @NamedQuery(name = "AppVersion.findAll", query = "select o from AppVersion o"),
                @NamedQuery(name = "AppVersion.findNewst", query = "select o from AppVersion o where o.status = 1 order by o.versionNumber DESC"),
                })
@Table(name = "APP_VERSION")
@Cacheable(false)
public class AppVersion {
    @Column(name = "CHANGE_LOG", length = 4000)
    private String changeLog;
    @Column(nullable = false)
    private Timestamp datetime;
    @Column(name = "DOWNLOAD_URL", nullable = false, length = 200)
    private String downloadUrl;
    @Column(name = "FILE_SIZE")
    private Long fileSize;
    @Column(nullable = false)
    private Integer forced;
    @Column(name = "VERSION_NUMBER", nullable = false)
    private Long versionNumber;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", 
                       sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
                    generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(name = "MD5_HASH", length = 50)
    private String md5Hash;
    @Column(length = 200)
    private String message;
    @Column(length = 100)
    private String name;
    @Column(name = "PACKAGE_NAME", length = 100)
    private String packageName;
    @Column(nullable = false)
    private Integer status;
    @Column(name = "VERSION_CODE", nullable = false, unique = true, length = 20)
    private String versionCode;
    @Column(name = "VERSION_NAME", length = 100)
    private String versionName;

    public AppVersion() {
    }

    public AppVersion(String changeLog, Timestamp datetime, String downloadUrl, Long fileSize, Integer forced, Long id,
                      String md5Hash, String message, String name, String packageName, Integer status,
                      String versionCode, String versionName) {
        this.changeLog = changeLog;
        this.datetime = datetime;
        this.downloadUrl = downloadUrl;
        this.fileSize = fileSize;
        this.forced = forced;
        this.id = id;
        this.md5Hash = md5Hash;
        this.message = message;
        this.name = name;
        this.packageName = packageName;
        this.status = status;
        this.versionCode = versionCode;
        this.versionName = versionName;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getForced() {
        return forced;
    }

    public void setForced(Integer forced) {
        this.forced = forced;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMd5Hash() {
        return md5Hash;
    }

    public void setMd5Hash(String md5Hash) {
        this.md5Hash = md5Hash;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Long getVersionNumber() {
        return versionNumber;
    }
}
