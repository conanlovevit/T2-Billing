package com.viettel.billing_log.bl.core;

import com.viettel.billing_log.api.BillingLog;

import com.viettel.billing_log.publicItem.PpmItem;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class UdpThread implements Runnable {
    private final int udpPort = 9883;
    private DatagramSocket serverSocket;
    private byte[] receiveData = new byte[1024];
    private boolean isPlay = true;
    private BillingLog ejb = null;
    
    public void start() throws SocketException {
        System.out.println("UdpThread - start");
        isPlay = true;
        serverSocket = new DatagramSocket(9883);
    }

    public void destroy() {
        System.out.println("UdpThread - destroy");
        isPlay = false;
        serverSocket.close();
    }
    
    @Override
    public void run() {
        while(isPlay) {
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);      
                String sentence = new String( receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("-------------------->" + sentence);
                PpmItem item = new PpmItem(sentence);
                if (item.isRebuy()) {
                    
                } else {
                    getEjb().ppm(item.getAccId(), item.getAccAccId(), item.getContentId(), item.getContentName(), item.getPrice(), item.getCreationDate(), item.getExpired());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("runing finish -----------------");
    }
    
    private BillingLog getEjb() throws Exception {
        if (ejb == null) {
            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            
            //        final Context context = new InitialContext();

            ejb = (BillingLog) context.lookup("ejb:Billing_Log_Ejb/billing_v2_log_ejb/BillingLog!com.viettel.billing_log.api.BillingLog");
        }
        return ejb;
    }
}
