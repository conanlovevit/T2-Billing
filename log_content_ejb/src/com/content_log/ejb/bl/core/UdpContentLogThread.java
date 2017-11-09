package com.content_log.ejb.bl.core;

import com.content_log.ejb.api.LogManager;

import com.content_log.ejb.publicItems.LogItem;

import com.ztv.oauth2.AccountInfo;

import com.ztv.oauth2.manager.Base64Coder;

import com.ztv.oauth2.manager.OAuth2Manager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class UdpContentLogThread implements Runnable {
    private final int udpPort = 39883;
    private DatagramSocket serverSocket;
    private byte[] receiveData = new byte[1024];
    private boolean isPlay = true;
    private LogManager ejb = null;
    
    public void start() throws SocketException {
        System.out.println("UdpThread - start");
        isPlay = true;
        serverSocket = new DatagramSocket(udpPort);
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
//                System.out.println("-------------------->" + sentence);
                LogItem item = new LogItem(sentence);
                if(item.getApp()!=null && item.getApp().equalsIgnoreCase("ITV")){
                    //JSONObject tokenObj = (JSONObject) JSONValue.parse(Base64Coder.decodeString(item.getToken()));                    
                    getEjb().addLog("1","deviceType", "android", "", 
                                    item.getService(),item.getApp(), item.getContentId(), item.getContentName(), 
                                    item.getDuration(), item.getBandwidth(), item.getSpeed(), item.getStreaming());
                }
                else{
                    JSONObject tokenObj = (JSONObject) JSONValue.parse(Base64Coder.decodeString(item.getToken()));
                    AccountInfo info = OAuth2Manager.verifyToken_notimeout(tokenObj);
                    getEjb().addLog(info.getDeviceId(), info.getDeviceType(), info.getOs(), info.getLocationCode(), 
                                    item.getService(),item.getApp(), item.getContentId(), item.getContentName(), 
                                    item.getDuration(), item.getBandwidth(), item.getSpeed(), item.getStreaming());
                }
                
               
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("runing finish -----------------");
    }
    
    private LogManager getEjb() throws Exception {
        if (ejb == null) {
            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);            
            //        final Context context = new InitialContext();

            ejb = (LogManager) context.lookup("ejb:/log_content_ejb//LogManager!com.content_log.ejb.api.LogManager");
        }
        return ejb;
    }
}

