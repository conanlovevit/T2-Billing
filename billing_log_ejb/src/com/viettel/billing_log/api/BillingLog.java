package com.viettel.billing_log.api;

import com.viettel.billing_log.publicItem.LogItem;

import java.util.List;

import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

@Remote
public interface BillingLog {
//    void createAccount(String accountId, String accountUsername);
    
    void ppm(String accountId, String accountUsername, String packageId, String packageName, long money, long creationDate, long expired);
    void rebuyppm(String accountId, String accountUsername, String packageId, String packageName, long money, long creationDate, long expired);
    
//    void ppv(String accountId, String accountUsername, String service, String contentId, String contentName, long money, long expired);
//    void ppv_gift(String accountId, String accountUsername, String service, String contentId, String contentName, String cpId, String cpName, long money, String dUsername, long expired);
//    void transfer(String accountId, String accountUsername, String dAccountId, String dAccountUsername, long money);
    void deposite_card(String accountId, String accountUsername, String operatorName, String cardSerial, String cardCode, long money);
//    void deposite_sms(String accountUsername, String sms_mobile, String sms_content, long money);
//    void deposite_anypay(String accountUsername, String type, String sessionId, long money);
//    
//    void customerCare_addMoney(String username, long money, String content);
//    void customerCare_subMoney(String username, long money, String content);
//    
//    String test();
}
