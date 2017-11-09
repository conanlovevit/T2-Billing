package com.idc.launcher.bridge.manager;

import com.idc.launcher.IdcLauncherEjb;
import com.idc.launcher.items.ItemAppVersion;
import com.idc.launcher.items.ItemLauncher;

import java.sql.Timestamp;

import java.util.List;

import java.util.Map;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LauncherManager {
    // caching params - start
    private final static long cache_timeout = 300000l;  // 5 minutes
    private static long cache_expired = 0;
    private static String cache_iconPath = null;
    private static List<ItemLauncher> cache_lstData = null;
    private static long cache_lastUpdated = 0;
    private static JSONObject cache_appVersion = null;
    // caching params - end
    
    
    private static JSONArray printItemList(List<ItemLauncher> lst, String iconPath, Map<String, JSONArray> posterMap) {
        if (lst == null || lst.size() == 0) return null;
        JSONArray ret = new JSONArray();
        for (int i = 0; i < lst.size(); i++) {
            ret.add(printItem(lst.get(i), iconPath, posterMap));
        }
        return ret;
    }
    
    private static JSONObject printItem(ItemLauncher item, String iconPath, Map<String, JSONArray> posterMap) {
        JSONObject ret = new JSONObject();
        ret.put("name", item.getName());
        ret.put("type", item.getType());
        if (item.getImage() != null)ret.put("image", iconPath + item.getImage());
        if (item.getIcon() != null) ret.put("icon", iconPath + item.getIcon());
        ret.put("url", item.getUrl());
        ret.put("id", item.getPackageId());
        ret.put("code", item.getCode());
        
        JSONArray childs = null;
        if (item.getType().equalsIgnoreCase("group")) childs = printItemList(item.getChilds(), iconPath, posterMap);
        if (item.getCode() != null && posterMap.containsKey(item.getCode())) childs = posterMap.get(item.getCode());
        if (childs != null) ret.put("childs", childs);
        return ret;
    }

    private static JSONObject printItemAppVersion(ItemAppVersion item) {
        if (item == null) return null;
        
        JSONObject ret = new JSONObject();
        ret.put("name", item.getName());
        ret.put("package", item.getPackageName());
        ret.put("version_code", item.getVersionNumber());
        ret.put("version", item.getVersionCode());
        ret.put("version_name", item.getVersionName());
        
        ret.put("forced", item.isForced());
        
        ret.put("url", item.getDownloadUrl());
        ret.put("size", item.getFileSize());
        ret.put("hash_md5", item.getMd5Hash());
        
        ret.put("message", item.getMessage());
        ret.put("change_log", item.getChangeLog());
        return ret;
    }
    
    private static synchronized boolean isNeedUpdateCache() {
        if (System.currentTimeMillis() > cache_expired) {
            cache_expired = System.currentTimeMillis() + cache_timeout;
            return true;
        }
        return false;
    }
    
    private static void getCache() throws Exception {
        if (isNeedUpdateCache()) {
            IdcLauncherEjb ejb = getEjb();
            cache_iconPath = ejb.getConfigFindByName("HTTP_ICON_ROOT");
            cache_lstData = ejb.getItemsFindAll("");
            long max = 0;
            for (int i = 0; i < cache_lstData.size(); i++)
                if (cache_lstData.get(i).getLastUpdate() > max) 
                    max = cache_lstData.get(i).getLastUpdate();
            cache_lastUpdated = max;
            
            cache_appVersion = printItemAppVersion(ejb.getNewestAppVersion());
        }
    }
    
    public static JSONObject getContent(Map<String, JSONArray> posterMap) throws Exception {
        getCache();

        JSONObject sRespone = new JSONObject();
        sRespone.put("last_update", cache_lastUpdated);
        sRespone.put("list", printItemList(cache_lstData, cache_iconPath, posterMap));
        return sRespone;
//        return printItemList(cache_lstData, cache_iconPath, posterMap);
    }
    
    public static JSONObject getAppVersionContent() throws Exception {
        getCache();

        return cache_appVersion;
    }
    
    public static JSONObject getContent(Map<String, JSONArray> posterMap, HttpServletRequest request, String customers) throws Exception {
        IdcLauncherEjb ejb = getEjb();
        String iconPath = ejb.getConfigFindByName("HTTP_ICON_ROOT");
        
        List<ItemLauncher> lst = ejb.getItemsFindAll(request.getParameter("id"));
        
        // find last update
        long max = 0;
        for (int i = 0; i < lst.size(); i++)
            if (lst.get(i).getLastUpdate() > max) 
                max = lst.get(i).getLastUpdate();
        
        
        JSONObject sRespone = new JSONObject();
        sRespone.put("last_update", max);
        sRespone.put("list", printItemList(lst, iconPath, posterMap));
        return sRespone;
    }
    
    public static IdcLauncherEjb getEjb() throws Exception {
        try {
            Properties props = new Properties();
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            InitialContext context = new InitialContext(props);
            
            return (IdcLauncherEjb) context.lookup("ejb:Launcher_ejb/IdcLauncherEjb/IdcLauncherEjb!com.idc.launcher.IdcLauncherEjb");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error at connect service in server Launcher EJB");            
        }
    }
}
