package com.idc.launcher;

import com.idc.launcher.items.ItemAppVersion;
import com.idc.launcher.items.ItemItem;
import com.idc.launcher.items.ItemLauncher;

import com.idc.launcher.items.ItemNotice;

import java.sql.Timestamp;

import java.util.Hashtable;

import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientContext;

public class IdcLauncherEjbClient {
    private static void printItem(ItemLauncher item, String tab) {
        System.out.println(tab + ": " + item.getName() + " - " + item.getPos());
        for (int i = 0; i < item.getChilds().size(); i++) {
            printItem(item.getChilds().get(i), tab + "\t");
        }
    }
    
    private static void editNotice(IdcLauncherEjb ejb) throws Exception {
        long time = 1000l * 60l * 60l * 1000l;
        ItemNotice item = ejb.getNoticeById(511l);
        item.setEnd_time(new Timestamp(System.currentTimeMillis() + time));
        ejb.editNotice(item);
    }
    
    private static void createNotice(IdcLauncherEjb ejb) throws Exception {
        ItemNotice item = new ItemNotice();
        item.setType(ItemNotice.TYPE_TEXT);
        item.setTitle("Title examples 1");
        item.setContent("Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1  Content examples 1");
        item.setImage("image1.jpg");
        item.setFrequency(new Long(300));
        item.setStart_time(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60));
        item.setEnd_time(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 1000));
        ejb.createNotice(item);

        item.setTitle("Title examples 2");
        item.setContent("Content examples 2  Content examples 2  Content examples 2  Content examples 2  Content examples 2  Content examples 2  " +
                        "Content examples 2  Content examples 2  Content examples 2  Content examples 2  Content examples 2  Content examples 2  " +
                        "Content examples 2  Content examples 2  Content examples 2  Content examples 2  Content examples 2  Content examples 2  " +
                        "Content examples 2  Content examples 2  Content examples 2");
        item.setImage("image2.jpg");
        item.setFrequency(new Long(300));
        item.setStart_time(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60));
        item.setEnd_time(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 1000));
        ejb.createNotice(item);
        
        item.setTitle("Title examples 3");
        item.setContent("Content examples 3  Content examples 3  Content examples 3  Content examples 3  Content examples 3  Content examples 3  " +
                        "Content examples 3  Content examples 3  Content examples 3  Content examples 3  Content examples 3  Content examples 3  " +
                        "Content examples 3  Content examples 3  Content examples 3  Content examples 3  Content examples 3  Content examples 3  " +
                        "Content examples 3  Content examples 3  Content examples 3");
        item.setImage("image3.jpg");
        item.setFrequency(new Long(30));
        item.setStart_time(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60));
        item.setEnd_time(new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 1000));
        ejb.createNotice(item);
        
        
    }
    
    public static void main(String[] args) {
        try {
            IdcLauncherEjb ejb = getEjb();
            List<ItemLauncher> lst = ejb.getItemsFindAll("A");
            for (int i = 0; i < lst.size(); i++) {
                printItem(lst.get(i), "  ");
            }
            
            ItemAppVersion item = ejb.getNewestAppVersion();
            System.out.println(item.getMd5Hash());
            
//            viettelLauncherEjb.getItemListByDevice(1l);
//            System.out.println(viettelLauncherEjb.getConfigFindByName("HTTP_ICON_ROOT"));
            //ItemItem item = viettelLauncherEjb.getItemById(12L);
            //item.setName(item.getName() + "1");
            //viettelLauncherEjb.editItem(item);
            //createNotice(viettelLauncherEjb);
            //editNotice(viettelLauncherEjb);
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static IdcLauncherEjb getEjb() throws Exception {
        try {
            final ContextSelector<EJBClientContext> test = JbossUtils.initializeEJBClientContext("123.30.145.221");
            EJBClientContext.setSelector(test);

            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            

            return (IdcLauncherEjb) context.lookup("ejb:Launcher_ejb/IdcLauncherEjb/IdcLauncherEjb!com.idc.launcher.IdcLauncherEjb");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error at connect service in server 10.10.10.31");
        }
    }
}
