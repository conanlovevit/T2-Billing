package com.idc.launcher.bridge.manager;

import com.vtce.vod.remote.attribute.VodJndi;
import com.vtce.vod.remote.bean.entity.Config;
import com.vtce.vod.remote.bean.entity.Vod;

import com.vtce.vod.remote.bean.session.LauncherBeanRemote;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class VodManager {
    // caching params - start
    private final static long cache_timeout = 300000l;  // 5 minutes
    private static long cache_expired = 0;
    private static JSONArray cache_lstData = null;
    // caching params - end
    
    private static JSONArray printItemList(List<Vod> lst) throws Exception {
        if (lst == null || lst.size() == 0) return null;
        JSONArray ret = new JSONArray();
        for (int i = 0; i < lst.size(); i++) {
            ret.add(printItem(lst.get(i)));
        }
        return ret;
    }
    
    private static JSONObject printItem(Vod item) throws Exception {
        JSONObject ret = new JSONObject();
        ret.put("id", item.getId());
        ret.put("name", item.getName());
        ret.put("type", "appinapp");
        ret.put("image",item.getPoster());
        ret.put("image_h",item.getPosterhorizon());
        ret.put("url","postervod://ztv.com.stb");
        ret.put("is_season", (item.getSerialContent() != null && item.getSerialContent().intValue() == 1));
        
        return ret;
    }
    
    private static synchronized  boolean isNeedUpdateCache() {
        if (System.currentTimeMillis() > cache_expired) {
            cache_expired = System.currentTimeMillis() + cache_timeout;
            return true;
        }
        return false;
    }
    
    private static void getCache() throws Exception {
        if (isNeedUpdateCache()) {
            cache_lstData = printItemList(getEjb().getLauncherItem("MOVIE"));
        }
    }
    
    public static JSONArray getContent() throws Exception {
        getCache();
        return cache_lstData;
    }
    
    private static LauncherBeanRemote getEjb() throws Exception {
        try {
            Properties props = new Properties();
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            
            InitialContext context = new InitialContext(props);
            
            return (LauncherBeanRemote) context.lookup(VodJndi.LAUNCHER_BEAN);
        } catch (Exception ex) {
            throw new Exception("Error at connect EJB service");            
        }
    }
    
    public static void main(String[] args) {
        try {
            System.out.println(getContent().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
