package com.content_log.ejb.api;

import com.content_log.ejb.bl.model.TblGcm;

import java.sql.Timestamp;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface GcmManager {
    List<TblGcm> getTblGcmFindActive(Timestamp yesterday);
}
