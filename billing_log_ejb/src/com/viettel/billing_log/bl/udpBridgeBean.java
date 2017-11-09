package com.viettel.billing_log.bl;

import com.viettel.billing_log.api.udpBridge;

import com.viettel.billing_log.bl.core.UdpThread;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton(name = "udpBridge", mappedName = "udpBridge")
@Startup
public class udpBridgeBean implements udpBridge {
    @Resource
    SessionContext sessionContext;
    
    private Thread thread;
    private UdpThread udpThread;

    public udpBridgeBean() {
    }
    
    @PostConstruct
    private void init() {
        System.out.println("*************************************");
        try {
            udpThread = new UdpThread();
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
