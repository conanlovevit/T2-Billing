package com.content_log.ejb.bl;

import com.content_log.ejb.api.GcmManager;
import com.content_log.ejb.api.GcmNotify;

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

import javax.ejb.Schedule;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton(name = "GcmNotify", mappedName = "ztv_t2-log_content_ejb-GcmNotify")
public class GcmNotifyBean implements GcmNotify {
    @Resource
    SessionContext sessionContext;
    
    @PersistenceContext(unitName = "log_content_ejb")
    private EntityManager em;

    private static final String _SERVER_KEY_ = "AIzaSyAnqkHrn4k0URBrxGE63LUJjeTALqmTvsk";
    private static final int _TTL_ = 10 * 60; //10 minutes

    public GcmNotifyBean() {
    }
    
//    @Schedule(hour = "*", minute = "*/10", info ="Every10  minutes timer", persistent=true)
    public void postGcm() {
        System.out.println("udpBridgeBean ---> postGcm");
        Vod vod = VodNotifyManager.getVod();
        if (vod == null)
            return;

        Message message = new Message.Builder()
            .timeToLive(_TTL_)
            .addData("type", "vod")
            .addData("id", vod.getId().toString())
            .addData("name", vod.getName())
            .addData("image", vod.getPosterhorizon())
            .addData("desc", vod.getDescription())
            .addData("rating", vod.getRating().toString())
            .build();
        System.out.println(message.toString());

        List<TblGcm> lst = getGcmList();
        int index = 0;
        Sender sender = new Sender(_SERVER_KEY_);
        while (index < lst.size()) {
            // create reg list
            List<String> regIds = new ArrayList<String>();
            for (int i = 0; i < 100; i++) {
                regIds.add(lst.get(index).getGcmId());
                if (++index >= lst.size())
                    break;
            }

            // send message
            try {
                if (regIds.size() > 0)
                    sender.send(message, regIds, 0);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }
    
    private GcmManager ejb = null;
    private List<TblGcm> getGcmList() {
        try {
            if (ejb == null) ejb = getEjb();
            Timestamp yesterday = new Timestamp(System.currentTimeMillis() - 86400000);
            return ejb.getTblGcmFindActive(yesterday);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<TblGcm>();
        }
    }

    private GcmManager getEjb() throws Exception {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);            
        //        final Context context = new InitialContext();

        return (GcmManager) context.lookup("ejb:/log_content_ejb//GcmManager!com.content_log.ejb.api.GcmManager");
    }
}
