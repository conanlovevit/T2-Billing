package com.content_log.ejb.bl.core;

import com.vtce.vod.remote.attribute.VodJndi;
import com.vtce.vod.remote.bean.entity.Vod;
import com.vtce.vod.remote.bean.session.LauncherBeanRemote;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

public class VodNotifyManager {
    private static int index = -1;
    private static boolean isRecommend = true;
    private static List<Vod> lst = null;
    
    public static Vod getVod() {
        getList();
        if (lst == null || index >= lst.size()) return null;
        return lst.get(index++);
    }
    
    public static void getList() {
        if (lst != null && index < lst.size()) return;
        try {
            if (isRecommend) {
                lst = getRecommendVod();
            } else {
                lst = getNewestVod();
            }
            isRecommend = !isRecommend;
            index = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static List<Vod> getRecommendVod() throws Exception {
        return getEjb().getLauncherItem("MOVIE");
    }
    
    private static List<Vod> getNewestVod() throws Exception {
        return getEjb().getUpdateVod("MOVIE", 0, 10);
    }
    
    private static LauncherBeanRemote __ejb__ = null;
    private static LauncherBeanRemote getEjb() throws Exception {
        if (__ejb__ != null) return __ejb__;
        try {
            Properties props = new Properties();
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            
            InitialContext context = new InitialContext(props);
            
            __ejb__ = (LauncherBeanRemote) context.lookup(VodJndi.LAUNCHER_BEAN);
            return __ejb__;
        } catch (Exception ex) {
            throw new Exception("Error at connect EJB service");            
        }
    }
}
