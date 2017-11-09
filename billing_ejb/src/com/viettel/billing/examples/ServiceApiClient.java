package com.viettel.billing.examples;

import com.viettel.billing.api.BridgeApi;
import com.viettel.billing.api.ServiceApi;

import com.viettel.billing.publicItem.ContentItem;

import com.viettel.billing.publicItem.consts.ServiceDefines;

import java.util.ArrayList;
import java.util.Hashtable;

import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;

public class ServiceApiClient {
    public static void main(String[] args) {
        try {
            ServiceApi ejb = getEjb();
            System.out.println(ejb.checkPlay(5735L));            
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private static ServiceApi getEjb() throws Exception {
        try {
            final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("123.30.145.221");
            EJBClientContext.setSelector(test);

            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            

            return (ServiceApi) context.lookup("ejb:Billing_Ejb/billing_v2_ejb/ServiceApi!com.viettel.billing.api.ServiceApi");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error at connect service in server 10.10.10.31");
        }
    }
}
