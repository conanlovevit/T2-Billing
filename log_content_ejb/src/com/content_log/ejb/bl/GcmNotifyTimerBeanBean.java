package com.content_log.ejb.bl;

import com.content_log.ejb.api.GcmManager;
import com.content_log.ejb.api.GcmNotifyTimerBean;

import com.content_log.ejb.bl.core.GcmSendingThread;
import com.content_log.ejb.bl.core.VodNotifyManager;
import com.content_log.ejb.bl.model.TblGcm;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

import com.vtce.vod.remote.bean.entity.Vod;

import java.io.IOException;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.Resource;

import javax.ejb.Local;
import javax.ejb.Schedule;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton(name = "GcmNotifyTimerBean", mappedName = "ztv_t2-log_content_ejb-GcmNotifyTimerBean")
public class GcmNotifyTimerBeanBean implements GcmNotifyTimerBean {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "log_content_ejb")
    private EntityManager em;


    public GcmNotifyTimerBeanBean() {
    }
    
    @Schedule(hour = "*", minute = "*/10", info ="Every10  minutes timer", persistent=true)
    public void postGcm() {
        Thread thread = new Thread(new GcmSendingThread());
        thread.start();
    }
}
