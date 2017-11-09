package com.viettel.billing.examples;

import com.viettel.billing.api.BridgeApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.io.Writer;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;

public class importDeviceClient {
    String importFile = "D:/ztvt2.csv";
    String resultFile = "D:/ztvt2_result.csv";
    
    public void run() throws Exception {
        BridgeApi ejb = getEjb();
        
        File result = new File(resultFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(
                                new FileOutputStream(result), "UTF8"));
        
        File fileDir = new File(importFile);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
        String str;
        while ((str = in.readLine()) != null) {
            String[] lstr = str.split(",", 2);    
            String mac = lstr[0].trim();
            String id = lstr[1].trim();
            try {
                ejb.createDevice(id, mac, null, "ztvt2");
            } catch (Exception ex) {
                out.append(mac + ":" + id + " --> " + ex.getMessage()).append("\r\n");
            }
//            System.out.println(id + " --> " + mac);
        }
        in.close();
        out.flush();
        out.close();
    }
    
    

    private static BridgeApi getEjb() throws Exception {
        try {
            //final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("10.10.10.253");
            final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("123.30.145.221");
            EJBClientContext.setSelector(test);

            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            

            return (BridgeApi) context.lookup("ejb:Billing_Ejb/billing_v2_ejb/BridgeApi!com.viettel.billing.api.BridgeApi");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error at connect service in server 10.10.10.31");
        }
    }
    public void run2() throws Exception {
        BridgeApi ejb = getEjb();
        
        //String str="";
        
            //String[] lstr = str.split(",", 2);    
            String mac = "";//lstr[0].trim();
            String id = "04-11 10:43:23.837: E/device_id(5472): A1-1-913-010592";//lstr[1].trim();
            try {
                ejb.createDevice(id, mac, null, "ztv5");
            } catch (Exception ex) {
                //out.append(mac + ":" + id + " --> " + ex.getMessage()).append("\r\n");
            }
  
    }
    
    public static void main(String[] args) throws Exception {
        importDeviceClient client = new importDeviceClient();    
        client.run2();
    }
}
