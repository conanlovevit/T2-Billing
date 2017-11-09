package com.viettel.billing.bl.core;

import com.viettel.billing.publicItem.AccountItem;

import com.viettel.billing.publicItem.consts.AccountStatusDefines;

import com.viettel.billing.publicItem.consts.ExceptionDefines;

import com.viettel.billing.utils.SecurityManager;

import java.sql.Timestamp;

import java.util.List;

import javax.persistence.EntityManager;

public class AccountManager {
    /*
    private static String standardMobile(String mobile) {
        if (mobile == null || mobile.length() <= 0) return "";
        
        if (!mobile.startsWith("84")) {
            while (mobile.startsWith("0")) {
                mobile = mobile.substring(1);
            }
            mobile = "84" + mobile;
        }
        return mobile;
    }
    
    public static String genPassword(TblAccount account, EntityManager em) throws Exception {
        String time = String.valueOf(System.currentTimeMillis());
        String newPwd = time.substring(time.length() - 5, time.length() - 1);
        String pwd_md5 = SecurityManager.md5_string(newPwd);
        
        account.setPassword(pwd_md5);
        em.merge(account);
        
        return newPwd;
    }
    public static TblAccount login(String username, String password, EntityManager em) throws Exception {
        TblAccount account = findAccountByUsername(username, em);
        if (account == null) throw new Exception(ExceptionDefines.ACCOUNT_NOTFOUND);
        
        if (account.getPassword() == null || !account.getPassword().toLowerCase().equalsIgnoreCase(password.toLowerCase())) 
            throw new Exception(ExceptionDefines.ACCOUNT_FAILEDLOGIN);
        return account;
    }
    
    public static AccountItem convertAccount(TblAccount acc) {
        if (acc == null) return null;
        
        AccountItem ret = new AccountItem();
        ret.setId(acc.getId());
        ret.setUsername(acc.getUsername());
        
        ret.setCreationDate(acc.getCreationDate());
        
        ret.setStatus(acc.getStatus());
        return ret;
    }
    
    public static TblAccount findOrCreateAccount(String username, EntityManager em) {
        username = standardMobile(username);
        List<TblAccount> lst = em.createNamedQuery("TblAccount.findExactlyByUsername", TblAccount.class)
            .setParameter("username", username.toLowerCase())
            .setMaxResults(1).getResultList();
        if (lst.size() > 0) {
            return lst.get(0);
        } else {
            TblAccount acc = new TblAccount();
            acc.setUsername(username);
            acc.setStatus(AccountStatusDefines.STATUS_ACTIVE);
            acc.setCreationDate(new Timestamp(System.currentTimeMillis()));
            em.persist(acc);
            return acc;
        }
    }
    
    public static List<TblAccount> findAccountListByUsername(String username, EntityManager em) {
        return em.createNamedQuery("TblAccount.findByUsername", TblAccount.class)
            .setParameter("username", "%" + username.toLowerCase() + "%")
            .setMaxResults(50)
            .getResultList();
    }
    
    public static TblAccount findAccountByUsername(String username, EntityManager em) {
        username = standardMobile(username);
        List<TblAccount> lst = em.createNamedQuery("TblAccount.findExactlyByUsername", TblAccount.class)
            .setParameter("username", username.toLowerCase())
            .setMaxResults(1).getResultList();
        return (lst.size() > 0 ? lst.get(0) : null);
    }
    
    public static void editAccount(AccountItem accountItem, EntityManager em) throws Exception {
        if (accountItem.getUsername() == null || accountItem.getUsername().length() == 0) throw new Exception(ExceptionDefines._WRONGPARAM);
        TblAccount acc = findAccountByUsername(accountItem.getUsername(), em);
        if (acc == null) throw new Exception(ExceptionDefines.ACCOUNT_NOTFOUND);
        
        acc.setStatus(accountItem.getStatus());
        em.merge(acc);
    }
    */
}
