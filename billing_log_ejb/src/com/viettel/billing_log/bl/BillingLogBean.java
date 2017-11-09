package com.viettel.billing_log.bl;

import com.viettel.billing_log.api.BillingLog;

import com.viettel.billing_log.bl.core.MyUtils;
import com.viettel.billing_log.bl.database.Account;

import com.viettel.billing_log.bl.database.Action;

import com.viettel.billing_log.bl.database.Content;
import com.viettel.billing_log.bl.database.ContentType;
import com.viettel.billing_log.bl.database.Cp;
import com.viettel.billing_log.bl.database.Log;
import com.viettel.billing_log.publicItem.ActionDefines;

import com.viettel.billing_log.publicItem.ContenTypeDefines;

import java.sql.Timestamp;

import java.util.Map;

import javax.annotation.Resource;

import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;

import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "BillingLog", mappedName = "BillingLog")
public class BillingLogBean implements BillingLog {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "billing_log_ejb")
    private EntityManager em;

    public BillingLogBean() {
    }

    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }
    
    private void createLog(Account account, Action action, Content content, long money, long createDate, long expired) {
        Log log = new Log();
        log.setAccount(account);
        log.setAction(action);
        if (content != null) log.setContent(content);
        
        log.setCreationDatetime(new Timestamp(createDate));
        if (expired > 0) log.setExpiredDatetime(new Timestamp(expired));
        
        log.setMoney(money);
        
        em.persist(log);
    }
/*
    @Override
    public void ppv(String accountId, String accountUsername, String service, String contentId, String contentName, long money, long expired) {
        Account account = MyUtils.findOrCreateAccount(accountId, accountUsername, em);
        Action action = MyUtils.findAction(ActionDefines.PPV, em);
        ContentType contentType = MyUtils.findContentType(ContenTypeDefines.CONTENT, em);
        
        Cp cp = MyUtils.findOrCreateCp(service, service, em);
        Content content = MyUtils.findOrCreateContent(contentId, contentName, contentType, cp, em);
        
        // create Log
        createLog(account, action, content, -money, System.currentTimeMillis(), expired);
    }
*/
//    Override
//    public void ppv_gift(String accountId, String accountUsername, String service, String contentId, String contentName,
//                         String cpId, String cpName, long money, String dUsername, long expired) {
//        Account account = MyUtils.findOrCreateAccount(accountId, accountUsername, em);
//        Action action = MyUtils.findAction(ActionDefines.GIFT, em);
//        ContentType contentType = MyUtils.findContentType(service, em);
//        Cp cp = MyUtils.findOrCreateCp(cpId, cpName, em);
//        Content content = MyUtils.findOrCreateContent(contentId, contentName, contentType, cp, em);
//        
//        // create Log
//        createLog(account, action, content, -money, expired, false, null, dUsername);
//    }

//    @Override
//    public void transfer(String accountId, String accountUsername, String dAccountId, String dAccountUsername,
//                         long money) {
//        Account account = MyUtils.findOrCreateAccount(accountId, accountUsername, em);
//        Account dAccount = MyUtils.findOrCreateAccount(dAccountId, dAccountUsername, em);
//        Action action = MyUtils.findAction(ActionDefines.TRANSFER, em);
//        
        // create Log of account
//        createLog(account, action, null, -money, System.currentTimeMillis(), -1, false, null, dAccountUsername);
        
        // create Log of dAccount
//        createLog(dAccount, action, null, money, System.currentTimeMillis(), -1, false, null, accountUsername);
//    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) 
    public void deposite_card(String accountId, String accountUsername, String operatorName, String cardSerial,
                              String cardCode, long money) {
        Account account = MyUtils.findOrCreateAccount(accountId, accountUsername, em);
        Action action = MyUtils.findAction(ActionDefines.DEPOSITE, em);
        ContentType contentType = MyUtils.findContentType(ContenTypeDefines.MOBILE_CARD, em);
        Content content = MyUtils.createContent(operatorName, cardSerial + "---" + cardCode, contentType, null, em);

//        createLog(account, action, content, money, System.currentTimeMillis(), -1, false, null, null);
        createLog(account, action, content, money, System.currentTimeMillis(), -1);
    }
/*
    @Override
    public void createAccount(String accountId, String accountUsername) {
        System.out.println("createAccount: " + accountId + " -- " + accountUsername);
        Account account = MyUtils.findOrCreateAccount(accountId, accountUsername, em);
        System.out.println("createAccount: " + account.getId());
        if (!account.getAccountUsername().equalsIgnoreCase(accountUsername)) {
            System.out.println("createAccount: " + accountUsername);
            account.setAccountUsername(accountUsername.toLowerCase());
            em.merge(account);
        }
    }
*/
//    @Override
//    public void deposite_sms(String accountUsername, String sms_mobile, String sms_content, long money) {
//        Account account = MyUtils.findAccountByUsername(accountUsername, em);
//        if (account == null) return;
//        Action action = MyUtils.findAction(ActionDefines.DEPOSITE, em);
//        ContentType contentType = MyUtils.findContentType(ContenTypeDefines.SMS, em);
//        Content content = MyUtils.createContent(sms_mobile, sms_content, contentType, null, em);
//
//        createLog(account, action, content, money, System.currentTimeMillis(), -1, false, null, null);
//    }

//    @Override
//    public void deposite_anypay(String accountUsername, String type, String sessionId, long money) {
//        Account account = MyUtils.findAccountByUsername(accountUsername, em);
//        if (account == null) return;
//        Action action = MyUtils.findAction(ActionDefines.DEPOSITE, em);
//        ContentType contentType = MyUtils.findContentType(ContenTypeDefines.ANYPAY, em);
//        Content content = MyUtils.createContent(type, sessionId, contentType, null, em);
//
//        createLog(account, action, content, money, System.currentTimeMillis(), -1, false, null, null);
//    }

//    @Override
//    public void customerCare_addMoney(String username, long money, String content) {
//        Account account = MyUtils.findAccountByUsername(username, em);
//        if (account == null) return;
//        Action action = MyUtils.findAction(ActionDefines.CUSTOMERCARE_ADD_MONEY, em);
//
//        createLog(account, action, null, money, System.currentTimeMillis(), -1, false, null, null);
//    }

//    @Override
//    public void customerCare_subMoney(String username, long money, String content) {
//        Account account = MyUtils.findAccountByUsername(username, em);
//        if (account == null) return;
//        Action action = MyUtils.findAction(ActionDefines.CUSTOMERCARE_SUB_MONEY, em);
//
//        createLog(account, action, null, -money, System.currentTimeMillis(), -1, false, null, null);
//    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) 
    public void ppm(String accountId, String accountUsername, String packageId, String packageName, long money, long creationDate, long expired) {
        Account account = MyUtils.findOrCreateAccount(accountId, accountUsername, em);
        Action action = MyUtils.findAction(ActionDefines.PPM, em);
        ContentType contentType = MyUtils.findContentType(ContenTypeDefines.PACKAGE, em);
        Content content = MyUtils.findOrCreateContent(packageId, packageName, contentType, null, em);
        
        // create Log
        createLog(account, action, content, -money, creationDate, expired);
        
//        logSubPackage(action, account, isPostpaid, PostpaidAccount, packageId, packageName, map, -money, creationDate, expired);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) 
    public void rebuyppm(String accountId, String accountUsername, String packageId, String packageName, long money, long creationDate, long expired) {
        Account account = MyUtils.findOrCreateAccount(accountId, accountUsername, em);
        Action action = MyUtils.findAction(ActionDefines.REBUY_PPM, em);
        ContentType contentType = MyUtils.findContentType(ContenTypeDefines.PACKAGE, em);
        Content content = MyUtils.findOrCreateContent(packageId, packageName, contentType, null, em);
        
        // create Log
        createLog(account, action, content, -money, creationDate, expired);
    }
    
    
//    private void logSubPackage(Action action, Account account, boolean isPostpaid, String PostpaidAccount, String packageId, String packageName, Map<Long, Integer> map, long money, long creationDate, long expired) {
//        System.out.println("logSubPackage: " + map);
//        if (map == null || map.get(-1l) == null) return ;
//        System.out.println("I am here");

//        Account defaultAccount = em.find(Account.class, 0l);
//
//        ContentType contentType = MyUtils.findContentType(ContenTypeDefines.SUB_PACKAGE, em);
//        
//        int total = map.get(-1l).intValue();
//        for (Long key : map.keySet()) {
//            if (key.longValue() == -1) continue;
//            
//            Cp cp = MyUtils.findOrCreateCp(String.valueOf(key.longValue()), null, em);
//            Content content = MyUtils.findOrCreateContent(packageId, packageName, contentType, cp, em);
//            
//            long newMoney = money * map.get(key) / total;
//            createLog(defaultAccount, action, content, newMoney, creationDate, expired, isPostpaid, PostpaidAccount, account.getAccountUsername());
//        }
//         
//    }

//    @Override
//    public String test() {
//        return "123";
//    }
}
