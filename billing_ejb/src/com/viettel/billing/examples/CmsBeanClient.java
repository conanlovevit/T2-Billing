package com.viettel.billing.examples;

import com.viettel.billing.api.BridgeApi;
import com.viettel.billing.api.CmsBean;

import com.viettel.billing.publicItem.ActiveItem;
import com.viettel.billing.publicItem.PackageItem;

import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;

public class CmsBeanClient {
    private static void createPackageExamples(CmsBean cmsBean) throws Exception {
        /*
        PackageItem item = new PackageItem();
        item.setName("Advanced MIO 1 - Premium");
        item.setPackageType(PackageTypeDefines.POST_PAID);
        
        item.setPrice(20000l);
        item.setDuration(1);
        item.setAutoRebuy(true);
        item.setExtendMobile(2);
        item.setExtendStb(0);
        
        item.setActive(true);
        item.setPromotion(false);
    
        
        System.out.println(cmsBean.createPackage(item));
*/
    }
    
    public static void printPackageItem(PackageItem item) {
        System.out.println("------------------");
        System.out.println("getId: " + item.getId());
        System.out.println("getName: " + item.getName());
        
        System.out.println("getPrice: " + item.getPrice());
        System.out.println("Duration: " + item.getDuration());
        
        System.out.println("isActive: " + item.isActive());
    }
    
    private static void listPackageExamples(CmsBean cmsBean) {
//        List<PackageItem> lst = cmsBean.getAllPackageList();
//        for (int i = 0; i < lst.size(); i++) {
//            printPackageItem(lst.get(i));
//        }
    }
    
    private static void deletePackageExamples(CmsBean cmsBean) throws Exception {
//        long id = 438l;
//        cmsBean.deletePackage(id);
    }
    
    private static void editPackageExamples(CmsBean cmsBean) throws Exception {
        /*
        long id = 438l;
        PackageItem item = cmsBean.getPackageById(id);        
        printPackageItem(item);

        item.setActive(false);
        cmsBean.editPackage(item);
        
//        PackageServiceItem psItem = item.getServices().get(0);
//        psItem.setFreeNumber(100);
//        psItem.setPricePercent(10);
//        cmsBean.editPackageService(psItem);
        
        item = cmsBean.getPackageById(id);        
        printPackageItem(item);*/
    }
    
    private static void promotionExamples(CmsBean cmsBean) throws Exception {/*
        PackagePromotionItem item = new PackagePromotionItem();
        item.setPackageId(500L);
        item.setPricePercent(50);
        
        item.setHourStart(16);
        item.setHourEnd(20);
        System.out.println(cmsBean.getPromotionByPackage(500).size());
        cmsBean.createPromotion(item);
        System.out.println(cmsBean.getPromotionByPackage(500).size());*/
    }
    private static void reportActive(CmsBean cmsBean) throws Exception {
        List<ActiveItem> lst= cmsBean.reportActive("20-11-2015","08-12-2015");
        for (int i = 0; i < lst.size(); i++) {
            System.out.println("stt -- "+i+". count="+lst.get(i).getCountActive()+"; date="+lst.get(i).getStrDate());
       }             
    }
    public static void main(String[] args) {
        try {
//            importDevice(null);
           // final Context context = getInitialContext();
            //CmsBean cmsBean = (CmsBean) context.lookup("CmsBean#com.viettel.billing.api.CmsBean");
            
            CmsBean ejb = getEjb();
            reportActive(ejb);
//            cmsBean.createDevice("e747f2b8e17084bf");            
//            importDevice(cmsBean);
            
//            promotionExamples(cmsBean);
            
//            createPackageExamples(cmsBean);
            //listPackageExamples(cmsBean);
//            deletePackageExamples(cmsBean);
//            editPackageExamples(cmsBean);
            
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static CmsBean getEjb() throws Exception {
        try {
            final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("123.30.145.221");
            //final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("10.10.10.253");
            EJBClientContext.setSelector(test);

            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            

            return (CmsBean) context.lookup("ejb:Billing_Ejb/billing_v2_ejb/CmsBean!com.viettel.billing.api.CmsBean");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error at connect service in server 10.10.10.31");
        }
    }
/*
    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x/12.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        //env.put(Context.PROVIDER_URL, "t3://172.16.238.137:7001");
        env.put(Context.PROVIDER_URL, "t3://10.10.10.253:7001");
        return new InitialContext(env);
    }*/
    
    
}
