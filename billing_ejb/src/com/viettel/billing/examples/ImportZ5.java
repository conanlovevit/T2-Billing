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

import java.util.Date;
import java.sql.Timestamp;

import java.text.DateFormat;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Hashtable;

import java.util.List;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;

public class ImportZ5 {
    public ImportZ5() {
        super();
    }
    
    //String importFile = "D:/ztvt2.csv";
    //String resultFile = "D:/ztvt2_result.csv";
    
    String importFile = "C:/Users/tung/Desktop/ztv5_billing.txt";    
    String resultFile = "C:/Users/tung/Desktop/ztv5_billing_result.txt";
    
    public void run() throws Exception {
        BridgeApi ejb = getEjb();
        
        File result = new File(resultFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(
                                new FileOutputStream(result), "UTF8"));
        
        File fileDir = new File(importFile);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
        String str;
        
        while ((str = in.readLine()) != null) {
           /* String[] lstr = str.split(",");    
            String mac = lstr[0].trim();
            String id = lstr[1].trim();
            try {
                ejb.createDevice(id, mac, null, "ztvt2");
            } catch (Exception ex) {
                out.append(mac + ":" + id + " --> " + ex.getMessage()).append("\r\n");
            }
            */
            String[] lstr = str.split(",",-1);    
            String deviceModel = lstr[0].trim();
            String deviceCode = lstr[1].trim();
            String account = lstr[2].trim();
            String activeDate= lstr[3].trim();
            DateFormat  formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss.SSSSSS a");
            String account_id="";
            if(activeDate.equals("")){
                java.util.Date date= new java.util.Date();
                activeDate=formatter.format((new Timestamp(date.getTime())));     
               account_id= deviceCode+"_"+date.getTime();
            }else{
             
                Date date = (Date) formatter.parse(activeDate);
                activeDate=formatter.format((new Timestamp(date.getTime())));
                account_id= deviceCode+"_"+date.getTime();
            }
           
            String expireDate= lstr[4].trim();            
            if(expireDate.equals("")){
                java.util.Date date= new java.util.Date();
                expireDate=formatter.format((new Timestamp(date.getTime())))+"";
                //expireDate=date.getTime()+"";
            }else{                
               
               
                Date date = (Date) formatter.parse(expireDate);
                java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
                
                Calendar cal = Calendar.getInstance();
                cal.setTime(timeStampDate);
                cal.add(Calendar.MONTH, 2);
                timeStampDate = new Timestamp(cal.getTime().getTime());                            
                expireDate  = formatter.format(timeStampDate);
               //expireDate  = timeStampDate.getTime()+"";
            }
            
            String balance = lstr[5].trim();
            System.out.println("deviceModel:"+deviceModel+";deviceCode:"+deviceCode+";account:"+
                               account+";account_id:"+account_id+";activeDate:"+activeDate+";expireDate:"+expireDate+";balance:"+balance);
            try {
                ejb.importZ5(deviceModel, deviceCode, account,account_id, activeDate,expireDate,balance);
            } catch (Exception ex) {
                ex.printStackTrace();
                out.append(str + " --> " + ex.getMessage()).append("\r\n");
            }
        }
        in.close();
        out.flush();
        out.close();
    }
       
    private static BridgeApi getEjb() throws Exception {
        try {
           // final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("10.10.10.253");
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
   // 
    public static void main(String[] args) throws Exception {
        ImportZ5 client = new ImportZ5();          
        client.run();
        
             // BridgeApi ejb = getEjb();
       
           // ejb.getBalance(93630l)
      //  System.out.println("balance:"+ejb.getBalance(93630l));
        
        
      // Format formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss.SSSSSS a");
        //   String today = formatter.format(new Date());
          // System.out.println("Today : " + today);
        
       
    }
}
