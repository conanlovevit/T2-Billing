package com.idc.launcher.items;

import java.io.Serializable;

import java.sql.Timestamp;

public class ItemAppVersion implements Serializable {
    private Long id;

    private String name;
    private String packageName;
    private Long versionNumber;
    private String versionCode;
    private String versionName;
    
    private boolean forced;
    private boolean active;
    
    private String downloadUrl;
    private Long fileSize;
    private String md5Hash;
    
    private String message;
    private String changeLog;
    private Timestamp datetime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public boolean isForced() {
        return forced;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setMd5Hash(String md5Hash) {
        this.md5Hash = md5Hash;
    }

    public String getMd5Hash() {
        return md5Hash;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Long getVersionNumber() {
        return versionNumber;
    }
}
