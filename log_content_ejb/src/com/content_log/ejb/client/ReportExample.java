package com.content_log.ejb.client;

import com.content_log.ejb.api.LogManager;

import com.content_log.ejb.api.Report;

import com.content_log.ejb.publicItems.BandWidthViewItem;

import com.content_log.ejb.publicItems.ViewItem;

import java.util.HashMap;
import java.util.Hashtable;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;

public class ReportExample {
    public ReportExample() {
        super();
    }
    public static void main(String[] args) throws Exception {
        Report ejb=getEjb();
        List<BandWidthViewItem> lst=ejb.reportBandWidthView("02-2014", "02-2016");
        //List<BandWidthViewItem> lst=ejb.reportBandWidthView(null, null);
        for (int i = 0; i < lst.size(); i++) {
            System.out.println(""+lst.get(i).getBandwidth()+"---------"+lst.get(i).getMonth());
       }
        List<ViewItem> lst1=ejb.reportView(null, null);
        for (int i = 0; i < lst1.size(); i++) {
           System.out.println("STT "+i+"---------"+"---------"+lst1.get(i).getStrDate());
           for (String key : lst1.get(i).getDataMap().keySet()) {
              
                   String s=lst1.get(i).getDataMap().get(key);   
               System.out.println("key:"+key+";value:"+s);
           }
       }

    }
    private static Report getEjb() throws Exception {
        try {
          //  final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("10.10.10.253");
          final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("123.30.145.221");

            EJBClientContext.setSelector(test);

            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            

            return (Report) context.lookup("ejb:/log_content_ejb//Report!com.content_log.ejb.api.Report");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error at connect service in server 10.10.10.31");
        }
    }
}
