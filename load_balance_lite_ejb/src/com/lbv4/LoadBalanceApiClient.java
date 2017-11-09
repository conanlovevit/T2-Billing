package com.lbv4;

import com.lbv4.api.LoadBalanceApi;

import com.lbv4.utils.LoadbalanceConfig;

import java.util.Hashtable;

import java.util.Properties;
import java.util.Random;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;

public class LoadBalanceApiClient {
    public static void main(String[] args) {
        try {
            LoadBalanceApi ejb = getEjb();
            System.out.println(ejb.getStreamingUrl("113.160.73.6", "nas2/2410_Giac_mo_Arizona___Arizona_Dream_tap_1_3394000.mp4", LoadbalanceConfig._CDN_VTCMEDIA_));
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static LoadBalanceApi getEjb() throws Exception {
        try {
            final ContextSelector<EJBClientContext> test = initializeEJBClientContext("123.30.145.221");
            EJBClientContext.setSelector(test);

            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            

            return (LoadBalanceApi) context.lookup(LoadbalanceConfig.JNDI);
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
        properties.put("remote.connection.default.port", "8330");
        properties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");

        PropertiesBasedEJBClientConfiguration configuration = new PropertiesBasedEJBClientConfiguration(properties);
        return new ConfigBasedEJBClientContextSelector(configuration);

    }
}
