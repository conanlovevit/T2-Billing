package com.content_log.ejb.bl;

import com.content_log.ejb.api.Report;

import com.content_log.ejb.publicItems.BandWidthViewItem;
import com.content_log.ejb.publicItems.ViewItem;

import java.util.ArrayList;
import java.util.Calendar;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.eclipse.persistence.jpa.rs.util.list.LinkList;

@Stateless(name = "Report", mappedName = "ztv_t2-log_content_ejb-Report")

public class ReportBean implements Report {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "log_content_ejb")
    private EntityManager em;

    public ReportBean() {
    }

    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }


    @Override
    public List<ViewItem> reportView(String fromDate, String toDate) throws Exception {
        List<ViewItem> ret = new ArrayList<ViewItem>();
        String sql =
            "select count(*) ,to_char(create_datetime,'dd-mm-yyyy HH24')  from tbl_log  " +          
            " where 1=1 " +
            (fromDate == null ? "" :
             (" AND TO_DATE(to_char(create_datetime, 'dd-mm-yyyy HH24:MI:SS'), 'dd-mm-yyyy HH24:MI:SS') > TO_DATE(?, 'dd-mm-yyyy HH24:MI:SS')")) +
            (toDate == null ? "" :
             (" AND TO_DATE(to_char(create_datetime, 'dd-mm-yyyy HH24:MI:SS'), 'dd-mm-yyyy HH24:MI:SS') < TO_DATE(?, 'dd-mm-yyyy HH24:MI:SS')")) +
            "  GROUP BY  to_char(create_datetime,'dd-mm-yyyy HH24')  "+
              " order by to_date(to_char(create_datetime,'dd-mm-yyyy HH24'),'dd-mm-yyyy HH24') DESC" ;

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
        ViewItem viewItemTemp = new ViewItem();
        Map<String, String> mapTempAll = new HashMap<String, String>();
        Map<String, String> mapTemp = new HashMap<String, String>();
        Map<String, String> mapDateTemp = new HashMap<String, String>();
        List<String> arrDate=new ArrayList<String>();
        for (Iterator it = temp.iterator(); it.hasNext();) {
            Object obj[] = (Object[]) it.next();
            if(mapDateTemp.get(obj[1] == null ? "" : obj[1].toString().substring(0, 10))==null){
                arrDate.add(obj[1] == null ? "" : obj[1].toString().substring(0, 10));
            }
            mapDateTemp.put(obj[1] == null ? "" : obj[1].toString().substring(0, 10),
                            obj[1] == null ? "" : obj[1].toString().substring(0, 10));
            
            mapTempAll.put(obj[1] == null ? "" : obj[1].toString(), obj[0] == null ? "" : obj[0].toString());
        }                
        String sTemp="";
        for (int k = 0; k < arrDate.size(); k++) {          
            viewItemTemp = new ViewItem();
            mapTemp = new HashMap<String, String>();
            for (int j = 0; j < 24; j++) {
                sTemp=("0"+j).substring(("0"+j).length()-2, ("0"+j).length());
                mapTemp.put(j + "", mapTempAll.get(arrDate.get(k) + " " + sTemp));
            }
            viewItemTemp.setDataMap(mapTemp);
            viewItemTemp.setStrDate(arrDate.get(k));
            ret.add(viewItemTemp);
        }
        
        return ret;
    }

    @Override
    public  List<BandWidthViewItem>  reportBandWidthView(String fromDate, String toDate) throws Exception {
       List<BandWidthViewItem>   ret = new  ArrayList<BandWidthViewItem> ();
        String sql ="select sum(bandwidth) ,to_char(create_datetime,'mm-yyyy')  from tbl_log " +
            " where 1=1 " +
            (fromDate == null ? "" :
             (" AND TO_DATE(to_char(create_datetime, 'dd-mm-yyyy HH24:MI:SS'), 'dd-mm-yyyy HH24:MI:SS') > TO_DATE(?, 'dd-mm-yyyy HH24:MI:SS')")) +
   //(toDate == null ? "" :
    //(" AND TO_DATE(to_char(create_datetime, 'dd-mm-yyyy HH24:MI:SS'), 'dd-mm-yyyy HH24:MI:SS') < TO_DATE(?, 'dd-mm-yyyy HH24:MI:SS')")) +
    (toDate == null ? "" :
     (" AND TO_DATE(to_char(create_datetime, 'mm-yyyy'), 'mm-yyyy') < ADD_MONTHS(TO_DATE(?, 'mm-yyyy'),1)")) +
   "  GROUP BY  to_char(create_datetime,'mm-yyyy')  " +
   " order by to_date(to_char(create_datetime,'mm-yyyy'),'mm-yyyy') DESC";
System.out.println("sql="+sql);
System.out.println("fromDate="+fromDate);
System.out.println("toDate="+toDate);
        Query query = em.createNativeQuery(sql);
        int i = 1;

        if (fromDate != null) {
            query.setParameter(i, "01-"+fromDate + " 00:00:00");
            i += 1;
        }
        if (toDate != null) {
            query.setParameter(i, toDate );
            i += 1;
        }

        List temp = query.getResultList();
        for (Iterator it = temp.iterator(); it.hasNext();) {
            Object obj[] = (Object[]) it.next();
            ret.add(new BandWidthViewItem(obj[1] == null ? "" : obj[1].toString(), Long.valueOf(obj[0] == null ? "0" : obj[0].toString())));
        }
        return ret;
    }
}
