package com.viettel.billing.bridge.manager;

import java.net.URLDecoder;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils_ {
    private static boolean isDebug = false;
    
    public static long logServletParam_start(HttpServletRequest request, String title) {
        if (!isDebug) return 0;
        System.out.println(title + ": ---------------- " + request.getRemoteAddr() + " === " + (new Date()).toString());
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println("\t" + paramName + " --> '" + request.getParameter(paramName) + "'");
        }
        return System.currentTimeMillis();
    }
    
    public static void logServletParam_finish(HttpServletRequest request, String title, String action, long __time__, Exception ex) {
        if (!isDebug) return;
        if (ex == null) {
            System.out.println(title + ": action=" + action + " --------------------" + request.getRemoteAddr() + " ===> " + (System.currentTimeMillis() - __time__));
        } else {
            System.out.println(title + ": action=" + action + " --------------------" + request.getRemoteAddr() + " ===> " + (System.currentTimeMillis() - __time__) + 
                               " ===> " + ex.getMessage());
        }
    }
    
    public static String getServletParam(HttpServletRequest request, String name) {
        try {
            String str = (String)request.getParameter(name);
            return str == null ? "" : str;
//            return Decode(str);
        } catch (Exception e) {
            return "";
        }
    }

    public static String Decode(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
    
    public static int getServletParam_int(HttpServletRequest request, String name, int defaultValue) {
        try {
            return Integer.parseInt(getServletParam(request, name));
        } catch (Exception ex) {
            return defaultValue;
        }
    }
}
