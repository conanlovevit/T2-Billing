package com.viettel.billing.bl.core;

import com.viettel.billing.publicItem.PackageItem;

import com.viettel.billing.publicItem.consts.AccountStatusDefines;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

public class PackageManager {
    /*
    public static PackageItem convertPackage(TblPackage item, EntityManager em) {
        PackageItem ret = new PackageItem();
        ret.setId(item.getId());
        ret.setName(item.getName());
        ret.setCode(item.getCode());
        
        ret.setPrice(item.getPrice());
        ret.setDuration(item.getDuration());
        ret.setDurationRetry(item.getDurationRetry());
        ret.setItemCount(item.getItemCount());
        
        ret.setActive(item.getStatus().intValue() == AccountStatusDefines.STATUS_ACTIVE);
        
        ret.setDescription(item.getDescription());

        ret.setService(item.getTblService().getName());

        return ret;
    }
    
    public static List<PackageItem> convertPackageList(List<TblPackage> lst, EntityManager em) {
        List<PackageItem> ret = new ArrayList<PackageItem>();
        for (int i = 0; i < lst.size(); i++)
            ret.add(convertPackage(lst.get(i), em));
        return ret;        
    }
    
    // status < 0 --> block
    // status = 0 --> all
    // status > 0 -> active
    public static List<TblPackage> getPackageList(TblService serviceObj, int status, EntityManager em) {
        String query = null;
        if (status == 0) query = "TblPackage.getAll";
        else if (status > 0) query = "TblPackage.getActive";
        else query = "TblPackage.getBlock";
        
        return em.createNamedQuery(query, TblPackage.class).setParameter("service", serviceObj).getResultList();        
    }
    
    public static TblPackage getPackageByName(String name, EntityManager em) {
        List<TblPackage> lst = em.createNamedQuery("TblPackage.findByName", TblPackage.class)
            .setParameter("name", name.toLowerCase())
            .setMaxResults(1)
            .getResultList();
        return lst.size() > 0 ? lst.get(0) : null;
    }
    
    public static TblPackage getPackageByCode(String code, EntityManager em) {
        List<TblPackage> lst = em.createNamedQuery("TblPackage.findByCode", TblPackage.class)
            .setParameter("code", code.toLowerCase())
            .setMaxResults(1)
            .getResultList();
        return lst.size() > 0 ? lst.get(0) : null;
    }
*/
}
