package com.viettel.billing_log.bl;

import com.viettel.billing_log.api.BillingLogReport;

import com.viettel.billing_log.bl.core.MyUtils;
import com.viettel.billing_log.bl.core.ReportManager;
import com.viettel.billing_log.bl.database.Account;

import com.viettel.billing_log.bl.database.Action;
import com.viettel.billing_log.bl.database.Content;
import com.viettel.billing_log.bl.database.ContentType;
import com.viettel.billing_log.bl.database.Cp;

import com.viettel.billing_log.bl.database.Log;

import com.viettel.billing_log.publicItem.ActionDefines;
import com.viettel.billing_log.publicItem.CardDepositeItem;
import com.viettel.billing_log.publicItem.ContentItem;
import com.viettel.billing_log.publicItem.LocationItem;
import com.viettel.billing_log.publicItem.LogItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(name = "BillingLogReport", mappedName = "BillingLogReport")
public class BillingLogReportBean implements BillingLogReport {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "billing_log_ejb")
    private EntityManager em;

    public BillingLogReportBean() {
    }

    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }

    @Override
    public List<LogItem> getLogByAccount(String accountUsername, int start, int number) {
        List<LogItem> lret = new ArrayList<LogItem>();
        
        // get account
        Account account = MyUtils.findAccountByUsername(accountUsername, em);
        if (account == null) return lret;
        
        List<Log> lst = MyUtils.getLogByAccount(account, start, number, em);
        
        for (int i = 0; i < lst.size(); i++) {
            Log log = lst.get(i);
            
            LogItem item = new LogItem();
            item.setAction(log.getAction().getName());
            if (log.getContent() != null) {
                item.setContent(log.getContent().getContentName());
                if (log.getContent().getContentType() != null) {
                    item.setContentType(log.getContent().getContentType().getName());
                }
            }
            item.setMoney(log.getMoney().longValue());
            item.setCreationDate(log.getCreationDatetime().getTime());
            item.setExpiredDate(log.getExpiredDatetime() == null ? -1 : log.getExpiredDatetime().getTime());
            lret.add(item);
        }
        
        return lret;
    }
/*
    @Override
    public Map<Long, String> getCpList() {
        List<Cp> lst = MyUtils.getAllCp(em);
        Map<Long, String> lret = new HashMap<Long, String>();

        for (int i = 0; i < lst.size(); i++) lret.put(lst.get(i).getId(), lst.get(i).getCpName());

        return lret;
    }

    @Override
    public List<ContentItem> findContent(String searchPattern, String serviceName, int start, int number) throws Exception {
        List<Content> lst = MyUtils.searchContent(searchPattern, serviceName, start, number, em);
        List<ContentItem> lret = new ArrayList<ContentItem>();
        for (int i = 0; i < lst.size(); i++) {
            ContentItem item = new ContentItem();
            item.setId(lst.get(i).getId());
            item.setContentName(lst.get(i).getContentName());
            lret.add(item);
        }
        return lret;
    }

    @Override
    public long reportByCp(long cpId, long start_datetime, long end_datatime) throws Exception {
        Cp cp = MyUtils.findCp(String.valueOf(cpId), em);
        if (cp == null) throw new Exception("Không tìm thấy CP có ID = " + cpId);

        return ReportManager.reportByCp(cp, start_datetime, end_datatime, em);
    }

    @Override
    public long reportByContent(long contentId, long start_datetime, long end_datatime) throws Exception {
        Content content = em.find(Content.class, contentId);
        if (content == null) throw new Exception("Không tìm thấy Content có ID = " + contentId);

        return ReportManager.reportByContent(content, start_datetime, end_datatime, em);
    }

    @Override
    public long reportByPaymentChannel(String paymentChannel, long start_datetime, long end_datatime) throws Exception {
        ContentType type = MyUtils.findContentType(paymentChannel, em);
        if (type == null) throw new Exception("Không tìm thấy ContentType: " + paymentChannel);

        return ReportManager.reportByContentType(type, start_datetime, end_datatime, em);
    }

    @Override
    public long reportByTrueContent(String service, String contentId, long start_datetime, long end_datatime) throws Exception {
        ContentType contentType = MyUtils.findContentType(service, em);
        if (contentType == null) throw new Exception("Không tìm thấy service: " + service);


        Content content = MyUtils.findContent(contentId, contentType, em);
        if (content == null) return 0L;

        return ReportManager.reportByContent(content, start_datetime, end_datatime, em);
    }

    @Override
    public long reportCountByCp(long cpId, long start_datetime, long end_datatime) throws Exception {
        Cp cp = MyUtils.findCp(String.valueOf(cpId), em);
        if (cp == null) throw new Exception("Không tìm thấy CP có ID = " + cpId);

        return ReportManager.reportCountByCp(cp, start_datetime, end_datatime, em);
    }

    @Override
    public long reportCountByTrueContent(String service, String contentId, long start_datetime,
                                         long end_datatime) throws Exception {
        ContentType contentType = MyUtils.findContentType(service, em);
        if (contentType == null) throw new Exception("Không tìm thấy service: " + service);


        Content content = MyUtils.findContent(contentId, contentType, em);
        if (content == null) return 0L;

        return ReportManager.reportCountByContent(content, start_datetime, end_datatime, em);
    }

    @Override
    public long reportCountByContent(long contentId, long start_datetime, long end_datatime) throws Exception {
        Content content = em.find(Content.class, contentId);
        if (content == null) throw new Exception("Không tìm thấy Content có ID = " + contentId);

        return ReportManager.reportCountByContent(content, start_datetime, end_datatime, em);
    }
*/
  

    @Override
    public List<LocationItem> reportLocation(String fromDate, String toDate, String location) throws Exception {
        // TODO Implement this method
        return Collections.emptyList();
    }

   

    @Override
    public List<CardDepositeItem> reportCardDeposite(String fromDate, String toDate) throws Exception {
        String sql =
                 "select sum(l.money),to_char(l.creation_datetime, 'dd-mm-yyyy') from log l inner join action ac on l.ACTION_ID=ac.ID " +                 
               
                  " where LOWER(ac.name)='" + ActionDefines.DEPOSITE + "' " +

                  (fromDate == null ? "" :
                   (" AND TO_DATE(to_char(l.creation_datetime, 'dd-mm-yyyy HH24:MI:SS'), 'dd-mm-yyyy HH24:MI:SS') > TO_DATE(?, 'dd-mm-yyyy HH24:MI:SS')")) +
                  (toDate == null ? "" :
                   (" AND TO_DATE(to_char(l.creation_datetime, 'dd-mm-yyyy HH24:MI:SS'), 'dd-mm-yyyy HH24:MI:SS') < TO_DATE(?, 'dd-mm-yyyy HH24:MI:SS')"))+
            "  GROUP BY  to_char(l.creation_datetime, 'dd-mm-yyyy') ORDER BY TO_DATE(to_char(l.creation_datetime, 'dd-mm-yyyy'),'dd-mm-yyyy') DESC ";

              Query query = em.createNativeQuery(sql);
              int i = 1;

              if (fromDate != null) {
                  query.setParameter(i, fromDate + " 00:00:00");
                  i += 1;
              }
              if (toDate != null) {
                  query.setParameter(i, toDate + " 23:59:59");
                  i += 1;
              }
             
              List temp = query.getResultList();
              CardDepositeItem tempDeposite = new CardDepositeItem();
              List<CardDepositeItem> result = new ArrayList<CardDepositeItem>();
              for (Iterator it = temp.iterator(); it.hasNext();) {
                  Object obj[] = (Object[]) it.next();
                  tempDeposite = new CardDepositeItem();
                  tempDeposite.setSumMoney(obj[0] == null ? 0 : Integer.valueOf(obj[0].toString()));
                  tempDeposite.setStrDate(obj[1] == null ? "" : obj[1].toString());                  
                  result.add(tempDeposite);
              }
              return result;
    }
}
