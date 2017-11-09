package com.viettel.billing.examples;

import com.viettel.billing.api.BridgeApi;

import com.viettel.billing.publicItem.AccountItem;

import com.viettel.billing.publicItem.MobileCardDepositeItem;
import com.viettel.billing.publicItem.PackageItem;

import com.viettel.billing.utils.SecurityManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.util.Hashtable;

import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;

public class BridgeApiClient {
    private static String genSign(String deviceCode, String mac, String serverKey) throws Exception {
        String plain = deviceCode + mac + serverKey;
        return SecurityManager.md5_string(plain.toLowerCase());
    }
    
    public static void main(String[] args) {
        try {
            BridgeApi ejb = getEjb();
//            boolean l=ejb.checkActive("222222");
//            System.out.println("lll+"+l);
//            ejb.createDevice("08221511000002", "00:1d:08:c8:23:7d", null, "ztvt2");
//            ejb.createDevice("08221511000003", "00:1d:08:c8:23:7e", null, "ztvt2");
      //      ejb.createDevice("08221511000004", "00:1d:08:c8:23:7f", null, "ztvt2");
//            System.out.println(ejb.getBalance(5802L));
            
//            ejb.activeDevice("82215120000168", genSign("82215120000168", "00:1d:08:b8:c0:66", "cenr484f4vrnuivtbbcru"));
            System.out.println(ejb.checkActive("82215120001348"));
//            
//            List<PackageItem> lst = ejb.getPackage(683L);
//            System.out.println(lst.size());
//            for (int i = 0; i < lst.size(); i++) 
//                System.out.println(lst.get(i).getDuration() + " - " + lst.get(i).getExpired());
//
//            MobileCardDepositeItem depositeItem = new MobileCardDepositeItem();
//            depositeItem.setCardType(MobileCardDepositeItem.TYPE_VIETTEL);
//            depositeItem.setCode("26994334966502");
//            depositeItem.setSerial("656223512878");
//            ejb.deposite(634l, depositeItem);

//            ejb.removePackage(435l, 458l);   
//            System.out.println(ejb.getBalance(471l));
//            ejb.buyPackage(683L, 0L);
//            List<PackageItem> lst = ejb.getPackage(471l);
//            System.out.println(lst.size());
//            System.out.println(lst.get(0).getPackageAccountId());
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
//            ex.printStackTrace();
        }
    }
        /*

    private static void loginExamples(BridgeApi ejb) throws Exception {
        AccountItem acc = ejb.login("09155528111", "7c39a5f991bef4a1e34187677a65871d", "3g");
        System.out.println(acc.getId() + " --> " + acc.getUsername());
    }

    private static void getPackageExamples(BridgeApi ejb) throws Exception {
        List<PackageItem> lst = ejb.getPackage("841234567891", "itv");
        for (int  i = 0; i < lst.size(); i++) {
            CmsBeanClient.printPackageItem(lst.get(i));
            if (lst.get(i).isUsed()) 
                System.out.println("--> " + lst.get(i).getExpired());
        }
    }

    private static void buyPackageExamples(BridgeApi ejb) throws Exception {
        List<PackageItem> lst = ejb.getPackage("09155528112", "film");
        
        System.out.println(ejb.buyPackage("09155528111", lst.get(1).getId()));
//        System.out.println(ejb.removePackage("09155528111", lst.get(1).getId()));
        
    }

    */

    private static BridgeApi getEjb() throws Exception {
        try {
            final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("123.30.145.221");
            //final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("10.10.10.253");
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
}
