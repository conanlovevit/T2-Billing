package com.viettel.billing_log.api;

import com.viettel.billing_log.bl.database.Account;
import com.viettel.billing_log.bl.database.Action;
import com.viettel.billing_log.bl.database.ContentType;
import com.viettel.billing_log.bl.database.Cp;
import com.viettel.billing_log.bl.database.Log;

import com.viettel.billing_log.publicItem.CardDepositeItem;
import com.viettel.billing_log.publicItem.ContentItem;
import com.viettel.billing_log.publicItem.LocationItem;
import com.viettel.billing_log.publicItem.LogItem;


import java.util.Date;
import java.util.List;

import java.util.Map;

import javax.ejb.Remote;

@Remote
public interface BillingLogReport {
    // history 
    List<LogItem> getLogByAccount(String accountUsername, int start, int number);
    
    // for report
    
    List<LocationItem> reportLocation(String fromDate,String toDate,String location) throws Exception;
    List<CardDepositeItem> reportCardDeposite(String fromDate,String toDate) throws Exception;;
    
    
    
    
    
    
//    Map<Long, String> getCpList();
//    List<ContentItem> findContent(String searchPattern, String serviceName, int start, int number) throws Exception;
    
//    long reportByCp(long cpId, long start_datetime, long end_datatime) throws Exception;
//    long reportByContent(long contentId, long start_datetime, long end_datatime) throws Exception;
//    long reportByTrueContent(String service, String contentId, long start_datetime, long end_datatime) throws Exception;
//    long reportByPaymentChannel(String paymentChannel, long start_datetime, long end_datatime) throws Exception;

//    long reportCountByCp(long cpId, long start_datetime, long end_datatime) throws Exception;
//    long reportCountByTrueContent(String service, String contentId, long start_datetime, long end_datatime) throws Exception;
//    long reportCountByContent(long contentId, long start_datetime, long end_datatime) throws Exception;
}
