package com.examples;

import com.viettel.billing_log.api.BillingLog;

import com.viettel.billing_log.api.BillingLogReport;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;

import sun.security.krb5.Config;

public class log_client {
    public static void main(String[] args) throws Exception {
        BillingLogReport ejb = getEjb();
        System.out.println(ejb.getLogByAccount("test", 0, 11));
    }

    private static BillingLogReport getEjb() throws Exception {
        try {
            final ContextSelector<EJBClientContext> test = initializeEJBClientContext("192.168.90.27");
            EJBClientContext.setSelector(test);

            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            

            return (BillingLogReport) context.lookup("ejb:Billing_Ejb/billing_v2_log_ejb/BillingLogReport!com.viettel.billing_log.api.BillingLogReport");
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
