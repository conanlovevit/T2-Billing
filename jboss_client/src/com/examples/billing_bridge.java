package com.examples;

import com.viettel.billing.api.BridgeApi;
import com.viettel.billing.publicItem.AccountItem;
import com.viettel.billing_log.api.BillingLog;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;

public class billing_bridge {
    public static void main(String[] args) throws Exception {
        BridgeApi ejb = getEjb();
        
//        AccountItem acc = ejb.getInfor(11l);
    }
    
    private static BridgeApi getEjb() throws Exception {
        try {
            final ContextSelector<EJBClientContext> test = initializeEJBClientContext("192.168.90.27");
            EJBClientContext.setSelector(test);

            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            

            return (BridgeApi) context.lookup("ejb:Billing_Ejb/billing_v2_ejb/BridgeApi!com.viettel.billing.api.BridgeApi");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error at connect service in server 192.168.90.27");
        }
    }

    private static ContextSelector<EJBClientContext> initializeEJBClientContext(String host) {
        Properties properties = new Properties();

        properties.put("endpoint.name", "client-endpoint");

        properties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");

        properties.put("remote.connections", "default");
        properties.put("remote.connection.default.host", host);
        properties.put("remote.connection.default.port", "8080");
        properties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");

        PropertiesBasedEJBClientConfiguration configuration = new PropertiesBasedEJBClientConfiguration(properties);
        return new ConfigBasedEJBClientContextSelector(configuration);

    }
}
