package com.viettel.billing_log.examples;

import com.viettel.billing_log.api.BillingLog;
import com.viettel.billing_log.api.BillingLogReport;

import com.viettel.billing_log.publicItem.ActionDefines;
import com.viettel.billing_log.publicItem.CardDepositeItem;
import com.viettel.billing_log.publicItem.ContenTypeDefines;

import com.viettel.billing_log.publicItem.ContentItem;
import com.viettel.billing_log.publicItem.LogItem;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;

public class BillingLogReportClient {
    private static void printLog(LogItem item) {
        System.out.println("creation_date: " + item.getCreationDate());
        System.out.println("expired_date: " + item.getExpiredDate());
        System.out.println("money: " + String.valueOf(item.getMoney() > 0 ? item.getMoney() : -item.getMoney()));
        
        if (ActionDefines.DEPOSITE.equalsIgnoreCase(item.getAction())) {
            if (ContenTypeDefines.MOBILE_CARD.equalsIgnoreCase(item.getContentType())) {
                System.out.println("action: Nạp tiền từ thẻ cào");
            }
            if (ContenTypeDefines.SMS.equalsIgnoreCase(item.getContentType())) {
                System.out.println("action: Nạp tiền từ SMS");
            }
            if (ContenTypeDefines.ANYPAY.equalsIgnoreCase(item.getContentType())) {
                System.out.println("action: Nạp tiền từ AnyPay");
            }
        }
        if (ActionDefines.PPM.equalsIgnoreCase(item.getAction())) {
            System.out.println("action: " + "Mua gói dịch vụ: " + item.getContent() + "");
        }
        if (ActionDefines.PPV.equalsIgnoreCase(item.getAction())) {
            System.out.println("getContentType: " + item.getContentType());
            
            if (ContenTypeDefines.SERVICE_GAMESTORE.equalsIgnoreCase(item.getContentType())) {
                System.out.println("action: " + "Mua phần mềm/game: " + item.getContent());
            }
            if (ContenTypeDefines.SERVICE_KARAOKE.equalsIgnoreCase(item.getContentType())) {
                System.out.println("action: " + "Thuê karaoke: " + item.getContent());
            }
            if (ContenTypeDefines.SERVICE_MCLIP.equalsIgnoreCase(item.getContentType())) {
                System.out.println("action: " + "Thuê clip: " + item.getContent());
            }
            if (ContenTypeDefines.SERVICE_MUSIC.equalsIgnoreCase(item.getContentType())) {
                System.out.println("action: " + "Thuê bài hát: " + item.getContent());
            }
            if (ContenTypeDefines.SERVICE_VOD.equalsIgnoreCase(item.getContentType()) || ContenTypeDefines.SERVICE_VOD_PREMIUM.equalsIgnoreCase(item.getContentType())) {
                System.out.println("action: " + "Thuê phim: " + item.getContent());
            }
        }
    }

    public static void main(String[] args) {
        try {
            BillingLogReport ejb = getEjb();

            //List<LogItem> lst = ejb.getLogByAccount("841234567890", 0, 100);
            //for (int i = 0; i < lst.size(); i++) {
             //   System.out.println("*****************");
             //   printLog(lst.get(i));
            //}
            List<CardDepositeItem> lst= ejb.reportCardDeposite("10-12-2015", "14-01-2016");
            for (int i = 0; i < lst.size(); i++) {
                System.out.println("stt----"+i+"---------"+lst.get(i).getSumMoney()+"----"+lst.get(i).getStrDate());
           }

        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static BillingLogReport getEjb() throws Exception {
        try {
           // final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("10.10.10.253");
           final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("123.30.145.221");

            
            EJBClientContext.setSelector(test);

            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            

            return (BillingLogReport) context.lookup("ejb:Billing_Log_Ejb/billing_v2_log_ejb/BillingLogReport!com.viettel.billing_log.api.BillingLogReport");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error at connect service in server 192.168.90.27");
        }
    }
}
