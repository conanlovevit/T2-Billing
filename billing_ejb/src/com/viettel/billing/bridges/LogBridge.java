package com.viettel.billing.bridges;

import com.viettel.billing.api.BridgeApi;
import com.viettel.billing.bl.database.TblAccount;
import com.viettel.billing.bl.database.TblPackage;
import com.viettel.billing.publicItem.PackageItem;
import com.viettel.billing_log.api.BillingLog;

import com.viettel.billing_log.publicItem.BillingLogUdpClient;

import java.util.HashMap;
import java.util.Hashtable;

import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class LogBridge {
    public static void ppm(TblAccount account, PackageItem ret) throws Exception {
        getEjb().ppm(String.valueOf(account.getId()), account.getAccountId(),  String.valueOf(ret.getId()), 
                                    ret.getName(), ret.getPrice(), 
                                    System.currentTimeMillis(), ret.getExpired().getTime());
//        BillingLogUdpClient.ppm(String.valueOf(account.getId()), account.getAccountId(), 
//                     String.valueOf(ret.getId()), ret.getName(), 
//                     ret.getPrice(), System.currentTimeMillis(), ret.getExpired().getTime());
    }

    public static void ppm_rebuy(TblAccount account, PackageItem ret) throws Exception {
        getEjb().rebuyppm(String.valueOf(account.getId()), account.getAccountId(),  String.valueOf(ret.getId()), 
                                    ret.getName(), ret.getPrice(), 
                                    System.currentTimeMillis(), ret.getExpired().getTime());
//        BillingLogUdpClient.ppm_rebuy(String.valueOf(account.getId()), account.getAccountId(), 
//                     String.valueOf(ret.getId()), ret.getName(), 
//                     ret.getPrice(), System.currentTimeMillis(), ret.getExpired().getTime());
    }


    private static BillingLog getEjb() throws Exception {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);            
//        final Context context = new InitialContext();            

        return (BillingLog) context.lookup("ejb:Billing_Log_Ejb/billing_v2_log_ejb/BillingLog!com.viettel.billing_log.api.BillingLog");
    }

}
