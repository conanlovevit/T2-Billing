package com.viettel.billing.bridge.manager;

import com.ztv.oauth2.OAuth2Verify;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

public class GcmManager {
    public static JSONObject getContent(HttpServletRequest request) throws Exception {
        String gcm_id = request.getParameter("gcm_id");
        long app_code = HttpUtils_.getServletParam_int(request, "version_code", 0);
        if (gcm_id == null || gcm_id.length() <= 0) throw new Exception("No register id found (gcm_id)!");
        getEjb().createGcm(OAuth2Verify.getAccount(request).getDeviceId(), gcm_id, app_code);
        return null;
    }
    
    private static com.content_log.ejb.api.LogManager getEjb() throws Exception {
        final Context context = getContext();            
        return (com.content_log.ejb.api.LogManager) context.lookup("ejb:/log_content_ejb//LogManager!com.content_log.ejb.api.LogManager");
    }
    
    private static Context getContext() throws NamingException {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        return new InitialContext(jndiProperties);            
    }
}
