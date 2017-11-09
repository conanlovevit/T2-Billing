package com.viettel.billing_log.bl.core;

import com.viettel.billing_log.bl.database.Content;
import com.viettel.billing_log.bl.database.ContentType;
import com.viettel.billing_log.bl.database.Cp;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ReportManager {
    private static String formatTimestamp(long time) {
        final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return format.format(new Timestamp(time));
        // CREATION_DATETIME > to_timestamp('22-12-2014 15:00:00', 'dd-mm-yyyy hh24:mi:ss')
    }
    
    private static long formatReportResult(BigDecimal result) {
        if (result == null) return 0;
        return result.longValue() > 0 ? result.longValue() : -result.longValue();
    }
    
    public static long reportCountByCp(Cp cp, long start_datetime, long end_datatime, EntityManager em) {
        String sql =    "SELECT COUNT(*)\n" + 
                        "FROM LOG, CONTENT\n" + 
                        "WHERE  LOG.CONTENT_ID = CONTENT.ID AND CONTENT.CP_ID = " + cp.getId().longValue() + " AND " +
                        "       LOG.CREATION_DATETIME >= to_timestamp('" + formatTimestamp(start_datetime) + "', 'dd-mm-yyyy hh24:mi:ss') AND " +
                        "       LOG.CREATION_DATETIME <= to_timestamp('" + formatTimestamp(end_datatime) + "', 'dd-mm-yyyy hh24:mi:ss')";
        Query query = em.createNativeQuery(sql);
        return formatReportResult((BigDecimal)query.getSingleResult());
    }
    
    public static long reportCountByContent(Content content, long start_datetime, long end_datatime, EntityManager em) {
        String sql =    "SELECT COUNT(*)\n" + 
                        "FROM LOG\n" + 
                        "WHERE LOG.CONTENT_ID = " + content.getId().longValue() + " AND " +
                        "       LOG.CREATION_DATETIME >= to_timestamp('" + formatTimestamp(start_datetime) + "', 'dd-mm-yyyy hh24:mi:ss') AND " +
                        "       LOG.CREATION_DATETIME <= to_timestamp('" + formatTimestamp(end_datatime) + "', 'dd-mm-yyyy hh24:mi:ss')";
        Query query = em.createNativeQuery(sql);
        return formatReportResult((BigDecimal)query.getSingleResult());
    }
    
    public static long reportByCp(Cp cp, long start_datetime, long end_datatime, EntityManager em) {
        String sql =    "SELECT sum(LOG.MONEY)\n" + 
                        "FROM LOG, CONTENT\n" + 
                        "WHERE  LOG.CONTENT_ID = CONTENT.ID AND CONTENT.CP_ID = " + cp.getId().longValue() + " AND " +
                        "       LOG.CREATION_DATETIME >= to_timestamp('" + formatTimestamp(start_datetime) + "', 'dd-mm-yyyy hh24:mi:ss') AND " +
                        "       LOG.CREATION_DATETIME <= to_timestamp('" + formatTimestamp(end_datatime) + "', 'dd-mm-yyyy hh24:mi:ss')";
        Query query = em.createNativeQuery(sql);
        return formatReportResult((BigDecimal)query.getSingleResult());
    }
    
    public static long reportByContent(Content content, long start_datetime, long end_datatime, EntityManager em) {
        String sql =    "SELECT sum(LOG.MONEY)\n" + 
                        "FROM LOG\n" + 
                        "WHERE LOG.CONTENT_ID = " + content.getId().longValue() + " AND " +
                        "       LOG.CREATION_DATETIME >= to_timestamp('" + formatTimestamp(start_datetime) + "', 'dd-mm-yyyy hh24:mi:ss') AND " +
                        "       LOG.CREATION_DATETIME <= to_timestamp('" + formatTimestamp(end_datatime) + "', 'dd-mm-yyyy hh24:mi:ss')";
        Query query = em.createNativeQuery(sql);
        return formatReportResult((BigDecimal)query.getSingleResult());
    }
    
    public static long reportByContentType(ContentType type, long start_datetime, long end_datatime, EntityManager em) {
        String sql =    "SELECT sum(LOG.MONEY)\n" + 
                        "FROM LOG, CONTENT\n" + 
                        "WHERE LOG.CONTENT_ID = CONTENT.ID AND CONTENT.CONTENT_TYPE_ID = " + type.getId().longValue() + " AND " +
                        "       LOG.CREATION_DATETIME >= to_timestamp('" + formatTimestamp(start_datetime) + "', 'dd-mm-yyyy hh24:mi:ss') AND " +
                        "       LOG.CREATION_DATETIME <= to_timestamp('" + formatTimestamp(end_datatime) + "', 'dd-mm-yyyy hh24:mi:ss')";
//        System.out.println(sql);
        Query query = em.createNativeQuery(sql);
        return formatReportResult((BigDecimal)query.getSingleResult());
    }
}
