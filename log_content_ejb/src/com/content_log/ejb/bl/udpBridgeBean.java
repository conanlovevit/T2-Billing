package com.content_log.ejb.bl;

import com.content_log.ejb.api.udpBridge;

import com.content_log.ejb.bl.core.UdpContentLogThread;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import javax.ejb.Schedule;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton(name = "udpBridge", mappedName = "udpBridge")
@Startup
public class udpBridgeBean implements udpBridge {
    @Resource
    SessionContext sessionContext;
    
    private Thread thread;
    private UdpContentLogThread udpThread;

    public udpBridgeBean() {
    }
    
    @PostConstruct
    private void init() {
        System.out.println("*************************************");
        try {
            udpThread = new UdpContentLogThread();
            udpThread.start();
            
            thread = new Thread(udpThread);
            thread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @PreDestroy
    private void destroy() {
        udpThread.destroy();
        thread.interrupt();
    }
}
