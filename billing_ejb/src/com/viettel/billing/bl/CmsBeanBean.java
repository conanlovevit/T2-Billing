package com.viettel.billing.bl;

import com.viettel.billing.api.CmsBean;

import com.viettel.billing.publicItem.ActiveItem;
import com.viettel.billing_log.publicItem.ActionDefines;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(name = "CmsBean", mappedName = "CmsBean")
public class CmsBeanBean implements CmsBean {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "billing_ejb")
    private EntityManager em;

    public CmsBeanBean() {
    }
    
    @Override
    public List<ActiveItem> reportActive(String fromDate, String toDate) throws Exception {
        String sql =
                 "select count(acc.CREATION_DATE),to_char(acc.CREATION_DATE, 'dd-mm-yyyy') from tbl_account acc where 1=1"+                            
                  (fromDate == null ? "" :
                   (" AND TO_DATE(to_char(acc.CREATION_DATE, 'dd-mm-yyyy HH24:MI:SS'), 'dd-mm-yyyy HH24:MI:SS') > TO_DATE(?, 'dd-mm-yyyy HH24:MI:SS')")) +
                  (toDate == null ? "" :
                   (" AND TO_DATE(to_char(acc.CREATION_DATE, 'dd-mm-yyyy HH24:MI:SS'), 'dd-mm-yyyy HH24:MI:SS') < TO_DATE(?, 'dd-mm-yyyy HH24:MI:SS')"))+
            "  GROUP BY  to_char(acc.CREATION_DATE, 'dd-mm-yyyy') ORDER BY TO_DATE(to_char(acc.CREATION_DATE, 'dd-mm-yyyy'),'dd-mm-yyyy') DESC ";

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
              ActiveItem tempActive = new ActiveItem();
              List<ActiveItem> result = new ArrayList<ActiveItem>();
              for (Iterator it = temp.iterator(); it.hasNext();) {
                  Object obj[] = (Object[]) it.next();
                  tempActive = new ActiveItem();
                  tempActive.setCountActive(obj[0] == null ? 0 : Integer.valueOf(obj[0].toString()));
                  tempActive.setStrDate(obj[1] == null ? "" : obj[1].toString());                  
                  result.add(tempActive);
              }
              return result;
    }
    
/*
    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }

    @Override
    public Long createPackage(PackageItem packageItem) throws Exception {
        TblPackage packageObj = PackageManager.createPackage(packageItem, em);
        
//        for (int i = 0; i < packageItem.getServices().size(); i++) {
//            PackageManager.createPackageService(packageObj, packageItem.getServices().get(i), em);
//        }
        
        return packageObj.getId();
    }

    @Override
    public List<PackageItem> getActivePackageList() {
        return PackageManager.getPackageList(1, em);
    }

    @Override
    public List<PackageItem> getBlockPackageList() {
        return PackageManager.getPackageList(-1, em);
    }

    @Override
    public List<PackageItem> getAllPackageList() {
        return PackageManager.getPackageList(0, em);
    }

    @Override
    public void deletePackage(Long id) throws Exception {
        PackageManager.deletePackage(id, em);
    }

    @Override
    public PackageItem editPackage(PackageItem packageItem) throws Exception {
        return PackageManager.editPackage(packageItem, em);
    }

    @Override
    public PackageItem getPackageById(Long id) {
        return PackageManager.getPackageById(id, em);
    }

    @Override
    public PackageServiceItem editPackageService(PackageServiceItem item) throws Exception {
//        return PackageManager.editPackageService(item, em);
        return null;
    }

    @Override
    public Long addPackageService(Long packageId, PackageServiceItem item) throws Exception {
//        return PackageManager.addPackageService(packageId, item, em);
        return null;
    }

    @Override
    public List<PackagePromotionItem> getPromotionByPackage(long packageId) throws Exception {
        TblPackage packageObj = em.find(TblPackage.class, packageId);
        if (packageObj == null) throw new Exception("Package không tồn tại!");
        
        List<TblPackagePromotion> lst = PackageManager.getPackagePromotionByPackage(packageObj, em);
        List<PackagePromotionItem> lret = new ArrayList<PackagePromotionItem>();
        
        for (int i = 0; i < lst.size(); i++) {
            lret.add(PackageManager.convertPackagePromotion(lst.get(i)));
        }
        
        return lret;
    }

    @Override
    public PackagePromotionItem createPromotion(PackagePromotionItem item) throws Exception {
        TblPackage packageObj = em.find(TblPackage.class, item.getPackageId());
        if (packageObj == null) throw new Exception("Package không tồn tại!");
        
        TblPackagePromotion obj = new TblPackagePromotion();
        
        obj.setPackageId(item.getPackageId());
        obj.setPricePercent(item.getPricePercent());
        
        obj.setHourStart(item.getHourStart());
        obj.setHourEnd(item.getHourEnd());
        obj.setDayOfWeekStart(item.getDayOfWeekStart());
        obj.setDayOfWeekEnd(item.getDayOfWeekEnd());
        obj.setDayOfMonthStart(item.getDayOfMonthStart());
        obj.setDayOfMonthEnd(item.getDayOfMonthEnd());
        obj.setMonthStart(item.getMonthStart());
        obj.setMonthEnd(item.getMonthEnd());
        obj.setYearStart(item.getYearStart());
        obj.setYearEnd(item.getYearEnd());
        
        em.persist(obj);
        
        item.setId(obj.getId());

        return item;
    }

    @Override
    public PackagePromotionItem editPromotion(PackagePromotionItem item) throws Exception {
        TblPackagePromotion obj = em.find(TblPackagePromotion.class, item.getId());
        if (obj == null) throw new Exception("Item không tồn tại!");
        
        obj.setPricePercent(item.getPricePercent());
        
        obj.setHourStart(item.getHourStart());
        obj.setHourEnd(item.getHourEnd());
        obj.setDayOfWeekStart(item.getDayOfWeekStart());
        obj.setDayOfWeekEnd(item.getDayOfWeekEnd());
        obj.setDayOfMonthStart(item.getDayOfMonthStart());
        obj.setDayOfMonthEnd(item.getDayOfMonthEnd());
        obj.setMonthStart(item.getMonthStart());
        obj.setMonthEnd(item.getMonthEnd());
        obj.setYearStart(item.getYearStart());
        obj.setYearEnd(item.getYearEnd());
        
        em.merge(obj);
        return item;
    }

    @Override
    public PackagePromotionItem getPromotionById(long promotionId) throws Exception {
        TblPackagePromotion obj = em.find(TblPackagePromotion.class, promotionId);
        if (obj == null) throw new Exception("Item không tồn tại!");
        return PackageManager.convertPackagePromotion(obj);
    }

    @Override
    public void removePromotion(long promotionId) {
        TblPackagePromotion obj = em.find(TblPackagePromotion.class, promotionId);
        if (obj != null) em.remove(obj);
    }

    @Override
    public void createDevice(String deviceId) {
        TblDevice obj = null;
        try {
            obj = DeviceManager.getDeviceByCode(deviceId, em);
            if (obj != null) return ;
        } catch (Exception e) {
        }
        
        obj = new TblDevice();
        obj.setCode(deviceId);
        obj.setCodeFull(deviceId);
        obj.setKey(deviceId);
        obj.setDatetime(new Timestamp(System.currentTimeMillis()));
        obj.setSerial(0);
        obj.setStatus(1);
        
        obj.setDeviceModel(em.find(TblDeviceModel.class, 6L));
        obj.setDeviceOs(em.find(TblDeviceOs.class, 0L));
        obj.setDeviceType(em.find(TblDeviceType.class, 0L));
        obj.setOsInfo(null);
        
        em.persist(obj);
    }
*/
}
