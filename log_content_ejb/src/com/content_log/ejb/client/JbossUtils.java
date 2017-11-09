package com.content_log.ejb.client;

import java.util.Properties;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;

public class JbossUtils {
    public static ContextSelector<EJBClientContext> initializeEJBClientContext(String host) {
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
