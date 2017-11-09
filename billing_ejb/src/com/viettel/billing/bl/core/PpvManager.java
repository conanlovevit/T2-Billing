package com.viettel.billing.bl.core;

import com.viettel.billing.publicItem.ContentItem;

import com.viettel.billing.publicItem.consts.ExceptionDefines;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PpvManager {
    /*
    public static TblServiceContent findContent(ContentItem contentItem, TblService serviceObj, EntityManager em) throws Exception {
        if (contentItem.getContentId() == null) throw new Exception(ExceptionDefines._WRONGPARAM);
        List<TblServiceContent> lst = em.createNamedQuery("TblServiceContent.findByContentIdAndService", TblServiceContent.class)
            .setParameter("service", serviceObj)
            .setParameter("contentId", contentItem.getContentId())
            .setMaxResults(1).getResultList();
        return lst.size() > 0 ? lst.get(0) : null;
    }
    
    public static TblServiceContent createOrFindContent(ContentItem contentItem, TblService serviceObj, EntityManager em) throws Exception {
        TblServiceContent ret = findContent(contentItem, serviceObj, em);
        if (ret != null) {
            return ret;
        } else {
            ret = new TblServiceContent();
            ret.setContentId(contentItem.getContentId());
            ret.setContentImage(contentItem.getContentImage());
            ret.setContentName(contentItem.getContentName());
            ret.setTblService(serviceObj);
            em.persist(ret);
            return ret;
        }
    }
    
    public static boolean isAvaialbeContent(TblAccount account, TblServiceContent content, EntityManager em) {
        List<TblServiceAccess> lst = em.createNamedQuery("TblServiceAccess.findActiveByAccountAndContent", TblServiceAccess.class)
            .setParameter("account", account)
            .setParameter("content", content)
            .setMaxResults(1).getResultList();
        return lst.size() > 0;
    }
    
    public static List<ContentItem> getAvaiableContent(TblAccount account, TblService serviceObj, EntityManager em) {
        Query q = em.createNativeQuery(
            "SELECT CONTENT_ID, CONTENT_NAME, CONTENT_IMAGE\n" + 
            "FROM VIEW_ACTIVE_SERVICE_ACCESS\n" + 
            "WHERE ACCOUNT_ID = " + account.getId().longValue() + " AND SERVICE_ID = " + serviceObj.getId().longValue() + "\n" + 
            "ORDER BY EXPIRED_DATE ASC");
        List lst = q.getResultList();
        List<ContentItem> lret = new ArrayList<ContentItem>();
        for (int i = 0; i < lst.size(); i++) {
            Object[] objectArray = (Object[])lst.get(i);
            ContentItem item = new ContentItem();
            item.setContentId(((BigDecimal)objectArray[0]).longValue());
            item.setContentName((String)objectArray[1]);
            item.setContentImage((String)objectArray[2]);
            lret.add(item);
        }
        return lret;
    }
    
    public static boolean checkContent(TblAccount account, ContentItem contentItem, TblService serviceObj, EntityManager em) throws Exception {
        TblServiceContent content = findContent(contentItem, serviceObj, em);
        if (content == null) return false;
        
        return isAvaialbeContent(account, content, em);
    }
    
    public static Long createOderLog(TblAccount account, TblService service, ContentItem contentItem, EntityManager em) {
        TblPpvOrderLog obj = new TblPpvOrderLog();
        obj.setMobile(account.getUsername());
        obj.setServiceId(service.getId());
        obj.setContentId(contentItem.getContentId());
        obj.setContentName(contentItem.getContentName());
        obj.setContentImage(contentItem.getContentImage());
        obj.setCreateDate(new Timestamp(System.currentTimeMillis()));
        obj.setPrice(contentItem.getPrice());
        obj.setDescription(contentItem.getDescription());
        em.persist(obj);
        
        return obj.getId();
    }
    
    public static void createServiceAccess(TblAccount account, TblService service, ContentItem contentItem, EntityManager em) throws Exception {
        final long minisecondOfday = 24 * 60 * 60 * 1000;
        
        TblServiceContent contentObj = createOrFindContent(contentItem, service, em);
        
        TblServiceAccess obj = new TblServiceAccess();
        obj.setTblAccount(account);
        obj.setTblServiceContent(contentObj);
        obj.setPrice(contentItem.getPrice());
        obj.setStartDate(new Timestamp(System.currentTimeMillis()));
        obj.setExpiredDate(new Timestamp(System.currentTimeMillis() + minisecondOfday * service.getPpvDuration()));
        em.persist(obj);
    }
    
    public static List<TblServiceAccess> getServiceAccess(TblAccount acc, EntityManager em) {
        List<TblServiceAccess> lst = em.createNamedQuery("TblServiceAccess.findByAccount", TblServiceAccess.class)
            .setParameter("account", acc)
            .getResultList();
        return lst;
    }
*/
}
