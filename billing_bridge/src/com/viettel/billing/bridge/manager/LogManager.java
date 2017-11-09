package com.viettel.billing.bridge.manager;

import com.viettel.billing_log.api.BillingLogReport;
import com.viettel.billing_log.publicItem.ActionDefines;
import com.viettel.billing_log.publicItem.ContenTypeDefines;
import com.viettel.billing_log.publicItem.LogItem;

import com.ztv.oauth2.OAuth2Verify;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LogManager {
    public static BillingLogReport getEjb() throws Exception {
        final Context context = getContext();            
        return (BillingLogReport) context.lookup("ejb:Billing_Log_Ejb/billing_v2_log_ejb/BillingLogReport!com.viettel.billing_log.api.BillingLogReport");
    }
    
    private static Context getContext() throws NamingException {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        return new InitialContext(jndiProperties);            
    }
    
    private static JSONObject printLog(LogItem item) {
        JSONObject ret = new JSONObject();
        ret.put("creation_date", item.getCreationDate());
        ret.put("expired_date", item.getExpiredDate());
        ret.put("money", item.getMoney() >= 0 ? item.getMoney() : -item.getMoney());
        
        if (ActionDefines.DEPOSITE.equalsIgnoreCase(item.getAction())) {
            if (ContenTypeDefines.MOBILE_CARD.equalsIgnoreCase(item.getContentType())) {
                ret.put("action", "action: Nạp tiền từ thẻ cào");
            }
        }
        if (ActionDefines.PPM.equalsIgnoreCase(item.getAction())) {
            ret.put("action", "Mua gói dịch vụ: " + item.getContent() + "");
        }
        if (ActionDefines.REBUY_PPM.equalsIgnoreCase(item.getAction())) {
            ret.put("action", "Gia hạn dịch vụ: " + item.getContent() + "");
        }
//        if (ActionDefines.PPV.equalsIgnoreCase(item.getAction())) {
//            ret.put("action", "Mua lẻ: " + item.getContent());
//        }
        return ret;
    }

    public static JSONObject getHistory(HttpServletRequest request) throws Exception {
        int start = HttpUtils_.getServletParam_int(request, "start", 0);
        int number = HttpUtils_.getServletParam_int(request, "number", 10);;
        
        List<LogItem> lst = getEjb().getLogByAccount(OAuth2Verify.getAccount(request).getAccountId(), start, number);
//        List<LogItem> lst = getEjb().getLogByAccount(HttpUtils_.getServletParam(request, "number"), service, start, number);
        JSONArray lret = new JSONArray();
        for (int i = 0; i < lst.size(); i++) {
            lret.add(printLog(lst.get(i)));
        }
        
        JSONObject ret = new JSONObject();
        ret.put("list", lret);
        return ret;
    }
}
