package com.viettel.billing.examples;

import com.viettel.billing.api.BridgeApi;
import com.viettel.billing.api.CustomerCare;

import com.viettel.billing.publicItem.CCAccount;
import com.viettel.billing.publicItem.PackageItem;

import java.util.Date;
import java.util.Hashtable;

import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;

public class CustomerCareClient {
    public static void main(String[] args) {
        try {
            CustomerCare ejb = getEjb();
            
//            List<CCAccount> lst = ejb.findAccount("71", 0, 10);
//            System.out.println(lst.size());
//            for (int i = 0; i < lst.size(); i++) {
//                CCAccount item = lst.get(i);
//                if (item.isActive()) {
//                    System.out.println(item.getDevice() + " --> " + item.getActiveDate() + " --> " + item.getGuaranteeExpired() + " --> " + item.getVipExpired());
//                } else {
//                    System.out.println(item.getDevice() + " is not active");
//                }
//            }

//            ejb.blockAccount(408, false);   
            
            ejb.activeDevice("012345");               
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
//            ex.printStackTrace();
        }
    }

    private static CustomerCare getEjb() throws Exception {
        try {
            final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("123.30.145.221");
            EJBClientContext.setSelector(test);

            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            
            return (CustomerCare) context.lookup("ejb:Billing_Ejb/billing_v2_ejb/CustomerCare!com.viettel.billing.api.CustomerCare");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error at connect service in server 10.10.10.31");
        }
    }
}
