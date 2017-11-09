package com.content_log.ejb.publicItems;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ContentLogClient {
    public static int _PORT_ = 39883;
    public static String _HOST_ = "apit2.ztv.vn";
    //public static String _HOST_ = "10.10.10.253";
    
    public static void sendLog(LogItem item) throws Exception {
        sendSocket(item.toString());
    }
    
    private static void sendSocket(String message) throws Exception {
        final InetAddress host = InetAddress.getByName(_HOST_) ;   

        DatagramSocket socket = new DatagramSocket();
        byte [] data = message.toString().getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, host, _PORT_) ;
        socket.send(packet) ;
        socket.close();
    }
    
    public static void main1(String[] args) throws Exception {
        String token = "eyJzIjoiN2NjOTk3YyIsImMiOnsidG8iOiIxNDQ5ODA2ODM1NDYwIiwicnRvIjoiMTQ0OTgwOTUzNTQ2MCIsImFjIjp7ImR0Ijoic3RiIiwiaWQiOiItMSIsIm9zIjoiYW5kcm9pZCIsImR2IjoidF9kZXZpY2UiLCJsYyI6IkhOIiwidXMiOiJ0X2FjY291bnQifX0sInJzIjoiOWNjM2E3ZCJ9";
        for (int i = 0; i < 1; i++) {
            LogItem item = new LogItem(token, "VOD",   "ITV",1,         "mo.mp4",    1,        1,       "trung binh", "http://streaming.vn/streaming");
            sendLog(item);     
        }
    }
    
    public static void main(String[] args) throws Exception {
        String token = "eyJzIjoiN2NjOTk3YyIsImMiOnsidG8iOiIxNDQ5ODA2ODM1NDYwIiwicnRvIjoiMTQ0OTgwOTUzNTQ2MCIsImFjIjp7ImR0Ijoic3RiIiwiaWQiOiItMSIsIm9zIjoiYW5kcm9pZCIsImR2IjoidF9kZXZpY2UiLCJsYyI6IkhOIiwidXMiOiJ0X2FjY291bnQifX0sInJzIjoiOWNjM2E3ZCJ9";
        for (int i = 0; i < 1; i++) {
            LogItem item = new LogItem(token, "VOD",   "ITV",1,         "1",  
                                       1,        1,   
                                       "828", "http://streaming.vn/streaming");
            sendLog(item);            
            
            //LogItem item = new LogItem(token, service, app, contentId, contentName, duration, bandwidth, speed, streaming);
        }
    }
}
