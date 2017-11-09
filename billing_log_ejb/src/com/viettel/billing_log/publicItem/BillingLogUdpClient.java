package com.viettel.billing_log.publicItem;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BillingLogUdpClient {
    public static int _PORT_ = 9883;
    public static String _HOST_ = "127.0.0.1";
    
    public static void ppm(String accountId, String accountUsername, String packageId, String packageName, long money, long creationDate, long expired) throws Exception {
        PpmItem item = new PpmItem(accountId, accountUsername, packageId, packageName, money, creationDate, expired);
        sendSocket(item.toString());            
    }
    
    public static void ppm_rebuy(String accountId, String accountUsername, String packageId, String packageName, long money, long creationDate, long expired) throws Exception {
        PpmItem item = new PpmItem(accountId, accountUsername, packageId, packageName, money, creationDate, expired, true);
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
    
    public static void main(String[] args) throws Exception {
    }
}
