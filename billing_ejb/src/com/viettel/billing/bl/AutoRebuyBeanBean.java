package com.viettel.billing.bl;

import com.viettel.billing.bl.core.PpmManager;

import com.viettel.billing.bl.database.TblAccount;
import com.viettel.billing.bl.database.TblPackage;
import com.viettel.billing.bridges.GatewayBridge;
import com.viettel.billing.bridges.LogBridge;
import com.viettel.billing.publicItem.PackageItem;
import com.viettel.billing.publicItem.consts.ExceptionDefines;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.util.List;

import javax.annotation.Resource;

import javax.ejb.Local;
import javax.ejb.Schedule;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimedObject;
import javax.ejb.Timer;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Singleton(name = "AutoRebuyBean", mappedName = "ztv_t2-billing_ejb-AutoRebuyBean")
@Startup
public class AutoRebuyBeanBean {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "billing_ejb")
    private EntityManager em;

    public AutoRebuyBeanBean() {
    }

//    @Schedule(hour="3", minute="1")
//    public void autoRebuy() {
//        System.out.println("-------- AutoRebuyBeanBean --> autoRebuy");
//        try {
//            autoExtensionPackage();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    private void autoExtensionPackage() throws Exception {
        while (true) {
            Query q = em.createNativeQuery("SELECT id, account_id, package_id FROM view_expired_pa").setMaxResults(1);
            List lst = q.getResultList();
            
            System.out.println("autoExtensionPackage --> " + lst.size());
            
            if (lst == null || lst.size() == 0) break;
            
            Object[] obj = (Object[]) lst.get(0);
            autoExtensionPackage(((BigDecimal) obj[0]).longValue(), ((BigDecimal) obj[1]).longValue(), ((BigDecimal) obj[2]).longValue());
        }
    }
    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) 
    private void autoExtensionPackage(long pa_id, long account_id, long package_id) throws Exception {
        System.out.println("autoExtensionPackage: " + account_id + " -->" + package_id);
        TblAccount account = em.find(TblAccount.class, account_id);
        TblPackage packageObj = em.find(TblPackage.class, package_id);

        PackageItem ret = new PackageItem();
        ret.setId(package_id);
        ret.setName(packageObj.getName());
        ret.setPrice(packageObj.getPrice());

        try {
            // payment
            GatewayBridge.ppm(account, ret.getName(), ret.getPrice());
        } catch (Exception e) {
            if (ExceptionDefines.PGT_NOT_ENOUGH_MONEY.equalsIgnoreCase(e.getMessage())) {
                // remove tbl_package_account if not enough money
                em.createNativeQuery("DELETE tbl_package_account WHERE id = ?").setParameter(1, pa_id).executeUpdate();
            } else {
                throw e;                
            }
        }

        // payment success
        Object[] lst = (Object[]) em.createNamedQuery("TblDevice.ppm")
                    .setParameter("iAccountId", account.getId())
                    .setParameter("iPackageId", package_id)
                    .getSingleResult();
        ret.setExpired((Timestamp) lst[1]);
        ret.setAutoRebuy((Integer) lst[2]);

        // log
        LogBridge.ppm_rebuy(account, ret);
    }
    
    
}
