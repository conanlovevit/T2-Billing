package com.content_log.ejb.client;

import com.content_log.ejb.api.LogManager;

import java.util.Hashtable;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;

public class LogManagerClient {
    public static void main(String[] args) {
        try {
            LogManager logManager = getEjb();
//            logManager.addLog("t_service", -1, "t_content", 1000, "eyJzIjoiMmYxNjM5ZSIsImMiOnsidG8iOiIxNDQ5NzQxMDY5NzIyIiwicnRvIjoiMTQ0OTc0Mzc2OTcyMiIsImFjIjp7ImR0Ijoic3RiIiwiaWQiOiItMSIsIm9zIjoiYW5kcm9pZCIsImR2IjoidF9kZXZpY2UiLCJsYyI6IkhOIiwidXMiOiJ0X2FjY291bnQifX0sInJzIjoiZTU3NTA1MSJ9");
//            logManager.addLog("l_deviceId", "l_deviceType", "l_deviceOs", "l_location", "l_service", 123, "l_deviceName", 3000, 123456, "slow", "");
            logManager.createGcm("12121212", "nothing", 12);
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static LogManager getEjb() throws Exception {
        try {
            final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("123.30.145.221");
            EJBClientContext.setSelector(test);

            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            

            return (LogManager) context.lookup("ejb:/log_content_ejb//LogManager!com.content_log.ejb.api.LogManager");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error at connect service in server 10.10.10.31");
        }
    }
}
