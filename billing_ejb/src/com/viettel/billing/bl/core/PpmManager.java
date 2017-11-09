package com.viettel.billing.bl.core;

import com.viettel.billing.bl.database.TblAccount;
import com.viettel.billing.bl.database.TblPackage;
import com.viettel.billing.bridges.GatewayBridge;
import com.viettel.billing.bridges.LogBridge;

import com.viettel.billing.publicItem.PackageItem;

import com.viettel.billing.publicItem.consts.ExceptionDefines;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PpmManager {
    public static void ppm_remove(Long accId, Long packageAccountId, EntityManager em) throws Exception {
        String oStatus = (String) em.createNamedQuery("TblDevice.ppm_remove")
            .setParameter("iAccountId", accId)
            .setParameter("iPackageAccountId", packageAccountId)
            .getSingleResult();
        
        if (oStatus.equalsIgnoreCase("accountIsNotVerify")) throw new Exception("Dữ liệu không phù hợp!");
    }
    
    public static PackageItem ppm(TblAccount account, Long packageId, EntityManager em) throws Exception {
        PackageItem ret = new PackageItem();
        ret.setId(packageId);

        Object[] lst = (Object[]) em.createNamedQuery("TblDevice.ppm_check")
            .setParameter("iAccountId", account.getId())
            .setParameter("iPackageId", packageId)
            .getSingleResult();
        String oStatus = (String) lst[0];
        Integer oNeedPayment = (Integer) lst[1];
        ret.setName((String) lst[2]);
        ret.setPrice(((Integer) lst[3]).longValue());

        if (oStatus.equalsIgnoreCase("packageNotFound")) throw new Exception("Không tìm thấy gói dịch vụ!");
        if (oNeedPayment == 0) return ret;
        
        // payment
        GatewayBridge.ppm(account, ret.getName(), ret.getPrice());
        
        // payment success 
        lst = (Object[]) em.createNamedQuery("TblDevice.ppm")
                    .setParameter("iAccountId", account.getId())
                    .setParameter("iPackageId", packageId)
                    .getSingleResult();
        oStatus = (String) lst[0];
        ret.setExpired((Timestamp) lst[1]);
        ret.setAutoRebuy((Integer) lst[2]);

        if (oStatus.equalsIgnoreCase("packageNotFound")) throw new Exception("Không tìm thấy gói dịch vụ!");
        
        // log
        LogBridge.ppm(account, ret);
        return ret;        
    }
    
    public static PackageItem convert(TblPackage obj) throws Exception  {
        if (obj == null) return null;
        PackageItem ret = new PackageItem();
        ret.setId(obj.getId());
        ret.setName(obj.getName());
        ret.setPrice(obj.getPrice());
        ret.setDuration(obj.getDuration());
        ret.setAutoRebuy(obj.getAutoRebuy());
        ret.setDescription(obj.getDescription());
        ret.setUsed(false);
        return ret;
    }
    
    // status < 0 --> block
    // status = 0 --> all
    // status > 0 -> active
    public static List<PackageItem> getPackageList(int status, EntityManager em) throws Exception  {
        String query = null;
        if (status == 0) query = "TblPackage.getAll";
        else if (status > 0) query = "TblPackage.getActive";
        else query = "TblPackage.getBlock";
        
        List<TblPackage> lst = em.createNamedQuery(query, TblPackage.class).getResultList();        
        List<PackageItem> lret = new ArrayList<PackageItem>();
        for (int i = 0; i < lst.size(); i++) {
            lret.add(convert(lst.get(i)));
        }
        return lret;
    }
    
    public static boolean isPlay(Long accId, EntityManager em) throws Exception  {
        List lst = em.createNativeQuery("SELECT a.id " + 
            "   FROM view_vip_account a " + 
            "   WHERE a.id = ?")
            .setParameter(1, accId)
            .getResultList();
        return lst.size() > 0;
    }
    
    public static List<PackageItem> getActivePackageAccountForItemCount(TblAccount acc, EntityManager em) throws Exception {
        List lst = em.createNativeQuery("SELECT a.id, a.package_id, a.expired_date, a.auto_rebuy, a.name, a.description, a.price, a.duration " + 
            "   FROM view_active_package_account a " + 
            "   WHERE a.account_id = ?")
            .setParameter(1, acc.getId())
            .getResultList();
        if (lst.size() == 0) return null;
        List<PackageItem> lret = new ArrayList<PackageItem>();
        for (int i = 0; i < lst.size(); i++) {
            lret.add(PackageItem.parseFromViewActivePackageAccount((Object[]) lst.get(i)));
        }
        return lret;
    }
}
