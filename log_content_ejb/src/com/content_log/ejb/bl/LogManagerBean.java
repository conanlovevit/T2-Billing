package com.content_log.ejb.bl;

import com.content_log.ejb.api.LogManager;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "LogManager", mappedName = "LogManager")
public class LogManagerBean implements LogManager {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "log_content_ejb")
    private EntityManager em;

    public LogManagerBean() {
    }

    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }

    @Override
    public void addLog(String deviceId, String deviceType, String deviceOs, String location, String service,String app, long contentId,
                       String contentName, long duraionSecond, long bandwidthKBytes, String speed, String streaming) throws Exception {
        if(app==null){
            app="ZTV-T2";    
        }
        
        em.createNamedQuery("TblLog.proc_insert_log")
            .setParameter("iDeviceId", deviceId)
            .setParameter("iDeviceType", deviceType)
            .setParameter("iDeviceOs", deviceOs)
            .setParameter("iLocation", location)
            .setParameter("iService", service)            
            .setParameter("iApp", app)
            .setParameter("iContentId", contentId)
            .setParameter("iContentName", contentName)
            .setParameter("iDuration", duraionSecond)
            .setParameter("iToken", null)
            .setParameter("iBandwidth", bandwidthKBytes)
            .setParameter("iSpeed", speed)
            .setParameter("iStreaming", streaming)
            .executeUpdate();
    }

  

    @Override
    public void createGcm(String deviceId, String gcmId, long appCode) {
        em.createNamedQuery("TblGcm.proc_gcm_create")
            .setParameter("iDeviceCode", deviceId)
            .setParameter("iGcmId", gcmId)
            .setParameter("iAppCode", appCode)
            .executeUpdate();
    }

    @Override
    public void updateGcm(String deviceId, long appCode) {
        em.createNamedQuery("TblGcm.proc_gcm_update")
            .setParameter("iDeviceCode", deviceId)
            .setParameter("iAppCode", appCode)
            .executeUpdate();
    }
}
