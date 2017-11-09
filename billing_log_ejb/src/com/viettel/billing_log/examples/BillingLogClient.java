package com.viettel.billing_log.examples;

import com.viettel.billing_log.api.BillingLog;

import java.util.Hashtable;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;

public class BillingLogClient {
    public static void main(String[] args) {
        try {
            BillingLog billingLog = getEjb();
//            billingLog.createAccount("1", "STSTST");
//            billingLog.deposite_sms("aaaaaaa", "8416680999996", "test", 15000);
//            billingLog.ppm("1", "STSTST1", "0", "test", 30000, System.currentTimeMillis(), System.currentTimeMillis() + 10123);
            billingLog.deposite_card("1", "STSTST1", "Viettel", "cardSerial", "cardCode", 23000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static BillingLog getEjb() throws Exception {
        try {
            final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("10.10.10.31");
            EJBClientContext.setSelector(test);

            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            

            return (BillingLog) context.lookup("ejb:Billing_Log_Ejb/billing_v2_log_ejb/BillingLog!com.viettel.billing_log.api.BillingLog");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error at connect service in server 10.10.10.31");
        }
    }
}
