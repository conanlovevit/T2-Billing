package com.viettel.billing_log.bl.core;

import com.viettel.billing_log.bl.database.Account;

import com.viettel.billing_log.bl.database.Action;

import com.viettel.billing_log.bl.database.Content;
import com.viettel.billing_log.bl.database.ContentType;

import com.viettel.billing_log.bl.database.Cp;

import com.viettel.billing_log.bl.database.Log;

import java.util.List;

import javax.persistence.EntityManager;

public class MyUtils {
    public static Account findOrCreateAccount(String accountId, String accountUsername, EntityManager em) {
        if (accountId == null || accountId.length() <= 0) return null;
        
        List<Account> lst = em.createNamedQuery("Account.findByAccountId", Account.class)
            .setParameter("account_id", accountId)
            .setMaxResults(1).getResultList();
        
        if (lst.size() > 0) {
            return lst.get(0);
        } else {
            Account account = new Account();
            account.setAccountId(accountId);
            account.setAccountUsername(accountUsername);
            em.persist(account);

            return account;
        }
    }
    
    public static Account findAccountByUsername(String accountUsername, EntityManager em) {
        if (accountUsername == null || accountUsername.length() <= 0) return null;
        
        List<Account> lst = em.createNamedQuery("Account.findByAccountUsername", Account.class)
            .setParameter("username", accountUsername.toLowerCase())
            .setMaxResults(1).getResultList();
        
        return lst.size() > 0 ? lst.get(0) : null;
    }
    
    public static List<Cp> getAllCp(EntityManager em) {
        return em.createNamedQuery("Cp.findAll", Cp.class).getResultList();
    }
    
    public static Cp findCp(String cpId, EntityManager em) {
        List<Cp> lst = em.createNamedQuery("Cp.findByCpId", Cp.class)
            .setParameter("cp_id", cpId.toLowerCase())
            .setMaxResults(1).getResultList();
        
        return lst.size() > 0 ? lst.get(0) : null;        
    }
    
    public static Cp findOrCreateCp(String cpId, String cpName, EntityManager em) {
        if (cpId == null || cpId.length() <= 0) return null;
        
        Cp cp = findCp(cpId, em);
        
        if (cp != null) {
            return cp;
        } else {
            cp = new Cp();
            cp.setCpId(cpId);
            cp.setCpName(cpName);
            em.persist(cp);

            return cp;
        }
    }
    
    private static boolean isEqual(ContentType a, ContentType b) {
        if (a == null && b == null) return true;
        if (a == null && b != null) return false;
        if (a != null && b == null) return false;
        return a.getId() == b.getId();
    }
    
    private static boolean isEqual(Cp a, Cp b) {
        if (a == null && b == null) return true;
        if (a == null && b != null) return false;
        if (a != null && b == null) return false;
        return a.getId() == b.getId();
    }
    
    public static List<Content> searchContent(String searchPattern, String typeName, int start, int number, EntityManager em) throws Exception {
        ContentType contentType = findContentType(typeName, em);
        if (contentType == null) throw new Exception("Không tìm thấy dịch vụ: " + typeName);
        
        List<Content> lst = em.createNamedQuery("Content.search", Content.class)
            .setParameter("contentName", "%" + searchPattern.toLowerCase() + "%")
            .setParameter("type", contentType)
            .setFirstResult(start)
            .setMaxResults(number)
            .getResultList();
        return lst;
    }
    
    public static Content findContent(String contentId, ContentType contentType, EntityManager em) {
        if (contentId == null || contentId.length() <= 0) return null;
        
        List<Content> lst = em.createNamedQuery("Content.findByContentId", Content.class)
            .setParameter("content_id", contentId.toLowerCase())
            .getResultList();
        for (int i = 0; i < lst.size(); i++) {
            if (isEqual(lst.get(i).getContentType(), contentType))
                return lst.get(i);
        }
        return null;
    }
    
    public static Content findOrCreateContent(String contentId, String contentName, ContentType contentType, Cp cp, EntityManager em) {
        if (contentId == null || contentId.length() <= 0) return null;
        
        List<Content> lst = em.createNamedQuery("Content.findByContentId", Content.class)
            .setParameter("content_id", contentId.toLowerCase())
            .getResultList();
        for (int i = 0; i < lst.size(); i++) {
            if (isEqual(lst.get(i).getContentType(), contentType) && isEqual(lst.get(i).getCp(), cp))
                return lst.get(i);
        }
        
        return createContent(contentId, contentName, contentType, cp, em);
    }
    
    public static Content createContent(String contentId, String contentName, ContentType contentType, Cp cp, EntityManager em) {
        if (contentId == null || contentId.length() <= 0) return null;
        
        Content content = new Content();
        content.setContentId(contentId);
        content.setContentName(contentName);
        content.setContentType(contentType);
        content.setCp(cp);
        em.persist(content);

        return content;
    }
    
    public static Action findAction(String name, EntityManager em) {
        List<Action> lst = em.createNamedQuery("Action.findByName", Action.class)
            .setParameter("name", name)
            .setMaxResults(1).getResultList();
        return lst.size() > 0 ? lst.get(0) : null;
    }
    
    public static ContentType findContentType(String name, EntityManager em) {
        List<ContentType> lst = em.createNamedQuery("ContentType.findByName", ContentType.class)
            .setParameter("name", name)
            .setMaxResults(1).getResultList();
        return lst.size() > 0 ? lst.get(0) : null;
    }
    
    public static List<Log> getLogByAccount(Account account, int start, int number, EntityManager em) {
        List<Log> lst = em.createNamedQuery("Log.findByAccount", Log.class)
            .setParameter("account", account)
            .setFirstResult(start).setMaxResults(number).getResultList();
        return lst;
    }
}
