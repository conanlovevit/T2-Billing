package com.viettel.billing.bl;

import com.viettel.billing.api.BridgeApi;
import com.viettel.billing.api.CustomerCare;

import com.viettel.billing.bl.core.ConfigManager;
import com.viettel.billing.bl.database.TblAccount;
import com.viettel.billing.bl.database.TblDevice;

import com.viettel.billing.publicItem.CCAccount;

import com.viettel.billing.utils.SecurityManager;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(name = "CustomerCare", mappedName = "CustomerCare")
public class CustomerCareBean implements CustomerCare {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "billing_ejb")
    private EntityManager em;
    
    @EJB
    BridgeApi bridgeApi;

    public CustomerCareBean() {
    }
    
    @Override
    public List<CCAccount> findAccount(String device, int start, int limit) {
        Query q = em.createNativeQuery("SELECT id, device_code, active_date, status, source, account_id, vip_expired, guarantee_expired, mac " + 
        "                               FROM view_cc_account a\n" + 
        "                               where a.device_code like ? \n" + 
        "                               order by device_code");
        q.setParameter(1, '%' + device + '%');
        q.setFirstResult(start).setMaxResults(limit);
        List lst = (List) q.getResultList();
        
        List<CCAccount> lret = new ArrayList<CCAccount>();
        for (int i = 0; i < lst.size(); i++) {
            Object[] objs = (Object[]) lst.get(i);
            
            CCAccount item = new CCAccount();
            item.setDevice((String) objs[1]);
            if (objs[0] == null) item.setActive(false);
            else {
                item.setActive(true);
                item.setId(((BigDecimal)objs[0]).longValue());
                item.setActiveDate(new Date(((Timestamp) objs[2]).getTime()));
                item.setBlock(((BigDecimal)objs[3]).longValue() == 0);
                item.setSource((String) objs[4]);
                item.setAccountId((String) objs[5]);
                if (objs[6] != null) {
                    Timestamp temp = (Timestamp) objs[6];
//                    if (temp.getTime() > System.currentTimeMillis())
//                        item.setVipExpired(new Date(temp.getTime()));
                    item.setVipExpired(new Date(temp.getTime()));
                }
                item.setGuaranteeExpired(new Date(((Timestamp) objs[7]).getTime()));
                item.setMac((String) objs[8]);
            }
            
            lret.add(item);
        }
        
        return lret;
    }

    @Override
    public void blockAccount(long id, boolean isBlock) throws Exception {
        TblAccount account = em.find(TblAccount.class, id);
        if (account == null) throw new Exception("No device found!");
        
        account.setStatus(isBlock ? 0 : 1);
        em.merge(account);
    }

    private static String genSign(String deviceCode, String mac, String serverKey) throws Exception {
        String plain = deviceCode + mac + serverKey;
        return SecurityManager.md5_string(plain.toLowerCase());
    }

    @Override
    public void activeDevice(String deviceCode) throws Exception {
        List<TblDevice> lst = em.createNamedQuery("TblDevice.findByCode", TblDevice.class)
            .setParameter("device_code", deviceCode)
            .setMaxResults(1).getResultList();
        if (lst.size() == 0) throw new Exception("Device '" + deviceCode + "' is not found!");
        
        TblDevice device = lst.get(0);
        String serverKey = ConfigManager.getConfig("ACTIVE_DEVICE_KEY", em).getValueString();
        
        String sign = null;
        if (device.getMac1() != null && device.getMac1().length() > 0) {
            sign = genSign(device.getCode(), device.getMac1(), serverKey);
        } else if (device.getMac2() != null && device.getMac2().length() > 0) {
            sign = genSign(device.getCode(), device.getMac2(), serverKey);
        } else {
            sign = genSign(device.getCode(), "", serverKey);
        }
        
        bridgeApi.activeDevice(device.getCode(), sign);
    }
}
