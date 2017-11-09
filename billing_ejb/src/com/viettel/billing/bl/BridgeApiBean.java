package com.viettel.billing.bl;

import com.viettel.billing.api.BridgeApi;

import com.viettel.billing.bl.core.AccountManager;
import com.viettel.billing.bl.core.ConfigManager;
import com.viettel.billing.bl.core.PackageManager;
import com.viettel.billing.bl.core.PpmManager;
import com.viettel.billing.bl.core.ServiceManager;
import com.viettel.billing.bl.database.TblAccount;
import com.viettel.billing.bl.database.TblDevice;
import com.viettel.billing.bl.database.TblPackage;
import com.viettel.billing.bridges.CardDepositeBridge;
import com.viettel.billing.bridges.GatewayBridge;
import com.viettel.billing.publicItem.AccountInforItem;
import com.viettel.billing.publicItem.AccountItem;
import com.viettel.billing.publicItem.MobileCardDepositeItem;
import com.viettel.billing.publicItem.PackageItem;

import com.viettel.billing.publicItem.applicationItem;
import com.viettel.billing.publicItem.consts.ExceptionDefines;

import java.math.BigDecimal;

import java.sql.Date;

import java.sql.Timestamp;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.eclipse.persistence.internal.jpa.StoredProcedureQueryImpl;

@Stateless(name = "BridgeApi", mappedName = "BridgeApi")
public class BridgeApiBean implements BridgeApi {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "billing_ejb")
    private EntityManager em;
    public BridgeApiBean() {
    }

    @Override
    public List<PackageItem> getPackage(Long accId) throws Exception {
        TblAccount account = em.find(TblAccount.class, accId);

        List<PackageItem> lst = PpmManager.getPackageList(1, em);
//        System.out.println("++++ getPackage: " + account);
        if (account != null && account.getVipExpired() != null && account.getVipExpired().getTime() > System.currentTimeMillis()) {
//            System.out.println("++++ getPackage" + account.getVipExpired());
            for (int i = 0; i < lst.size(); i++) {
                lst.get(i).setExpired(account.getVipExpired());
            }
        }
        return lst;

//        if (account != null) {
//            List<PackageItem> lstUsed = PpmManager.getActivePackageAccountForItemCount(account, em);
//            if (lstUsed != null) return lstUsed;
//        }
//
//        return PpmManager.getPackageList(1, em);
    }

    @Override
    public PackageItem buyPackage(Long accId, Long packageId) throws Exception {
        // get account
        TblAccount account = em.find(TblAccount.class, accId);
        if (account == null) throw new Exception(ExceptionDefines.ACCOUNT_NOTFOUND);

        return PpmManager.ppm(account, packageId, em);
    }

    @Override
    public void removePackage(Long accId, Long packageAccountId) throws Exception {
        PpmManager.ppm_remove(accId, packageAccountId, em);
    }

    @Override
    public AccountItem login(String deviceCode, String time, String macSign) throws Exception {
        Object[] lst = (Object[]) em.createNamedQuery("TblDevice.login")
            .setParameter("iDeviceCode", deviceCode)
            .setParameter("iTime", time)
            .setParameter("iSign", macSign)
            .getSingleResult();
        String oStatus = (String) lst[0];
        Integer oId = (Integer) lst[1];
        String oAccountId = (String) lst[2];
        String oDeviceType = (String) lst[3];
        String oDeviceOs = (String) lst[4];
        String oLocation = (String) lst[5];
        
        if (oStatus.equalsIgnoreCase("done")) {
            AccountItem acc = new AccountItem();
            acc.setId(new Long(oId));
            acc.setAccountId(oAccountId);
            acc.setDeviceId(deviceCode);
            acc.setDeviceType(oDeviceType);
            acc.setDeviceOs(oDeviceOs);
            acc.setLocation(oLocation);
            
            return acc;
        } else {
            // exception
            if (oStatus.equalsIgnoreCase("accountIsNotActive")) throw new Exception("Error: Thiết bị chưa được kích hoạt");
            if (oStatus.equalsIgnoreCase("deviceNotFound")) throw new Exception("Error: Không tìm thấy mã thiết bị '" + deviceCode + "'");
            if (oStatus.equalsIgnoreCase("accountIsBlock")) throw new Exception("Error: Tài khoản bị khóa");
            if (oStatus.equalsIgnoreCase("deviceNotVerify")) throw new Exception("Error: Xác thực thiết bị không đúng!");
            return null;
        }
    }

    @Override
    public boolean checkActive(String deviceCode) throws Exception {
        Query q = em.createNativeQuery("SELECT check_device_active(?) FROM dual\n");
        q.setParameter(1, deviceCode);
        BigDecimal number = (BigDecimal) q.getSingleResult();
        return number.longValue() > 0;
    }

    @Override
    public void activeDevice(String deviceCode, String sign) throws Exception {        
        Object[] lst = (Object[]) em.createNamedQuery("TblDevice.register")
            .setParameter("iDeviceCode", deviceCode)
            .setParameter("iSign", sign.toLowerCase())
            .getSingleResult();
        
        String oStatus = (String) lst[0];
        Integer oId = (Integer) lst[1];
        String oAccountId = (String) lst[2];
        Integer oNeedCreateAccount = (Integer) lst[3];        
        if (oStatus.equalsIgnoreCase("done")) {
            if (oNeedCreateAccount > 0) {
                // paygate
                GatewayBridge.createAccount(oAccountId);
            }
        } else {
            // exception
            if (oStatus.equalsIgnoreCase("deviceAlreadyActive")) throw new Exception("Error: Thiết bị đã được kích hoạt");
            if (oStatus.equalsIgnoreCase("deviceNotFound")) throw new Exception("Error: Không tìm thấy mã thiết bị '" + deviceCode + "'");
            if (oStatus.equalsIgnoreCase("vitualDeviceIsNotSupport")) throw new Exception("Error: Thiết bị ảo chưa được hỗ trợ");
            if (oStatus.equalsIgnoreCase("deviceNotVerify")) throw new Exception("Error: Xác thực thiết bị không đúng!");
        }
    }

    @Override
    public void createDevice(String deviceCode, String mac1, String mac2, String deviceModelCode) throws Exception {
        String oStatus = (String) em.createNamedQuery("TblDevice.create_device")
            .setParameter("iDeviceModelCode", deviceModelCode)
            .setParameter("iDeviceCode", deviceCode)
            .setParameter("iMac1", mac1)
            .setParameter("iMac2", mac2)
            .getSingleResult();
//        String oStatus = (String) lst[0];
        if (oStatus.equalsIgnoreCase("deviceModelNotFound")) throw new Exception("Error: Không tìm thấy model thiết bị '" + deviceModelCode + "'");
        if (oStatus.equalsIgnoreCase("deviceCodeExist")) throw new Exception("Error: mã thiết bị '" + deviceCode + "' đã tồn tại");
    }
    public void importZ5(String iDeviceModelCode, String deviceCode,String  account,String  accountId,String  activeDate,
                         String expireDate,String balance) throws Exception{
        
         Object[] lst = (Object[]) em.createNamedQuery("TblDevice.import_z5")
            .setParameter("iDeviceModelCode", iDeviceModelCode)
            .setParameter("iDeviceCode", deviceCode)
            .setParameter("iAccountName", account)
             .setParameter("iActiveDate", activeDate)
            .setParameter("iExpireDate", expireDate)
        .setParameter("iAccountId", accountId)
            .getSingleResult();
             String oStatus = (String) lst[0];
            // String oAccountId = (String) lst[1];
            
        if (oStatus.equalsIgnoreCase("accountIdExist")) throw new Exception("Error: Tài khoản đã tồn tại '" + accountId + "'");
        if (oStatus.equalsIgnoreCase("deviceCodeExist")) throw new Exception("Error: mã thiết bị '" + deviceCode + "' đã tồn tại");
        if(!oStatus.equalsIgnoreCase("done"))  throw new Exception("Error: Lỗi không xác định mã thiết bị:'" + deviceCode + "';Tài khoản '" + accountId + "'");
        if(accountId!=null && !accountId.trim().equals("")){            
        try{
            GatewayBridge.createAccount(accountId);
        }catch(Exception ex){            
            }
        }
        if(oStatus.equalsIgnoreCase("done") && !balance.trim().equals("") && !accountId.trim().equals("")){            
            GatewayBridge.Z5_addMoney(accountId,Long.valueOf(balance));
        }
        
    }
    
    @Override
    public List<applicationItem> getAppByVersion(String version) {
        List<applicationItem> lret = new ArrayList<applicationItem>();
        
        String imageRoot = ConfigManager.getAppImageRoot(em);
        
        List lst = em.createNamedQuery("SELECT a.name, a.image, a.package\n" + 
        "  FROM view_app_version a\n" + 
        "  where LOWER(a.version_code) = LOWER('?')")
            .setParameter(1, version)
            .getResultList();
        
        for (int i = 0; i < lst.size(); i++)  {
            lret.add(new applicationItem((Object[])lst.get(i), imageRoot));
        }
        
        return lret;
    }

    @Override
    public long getBalance(Long accId) throws Exception {
        // get account
        TblAccount account = em.find(TblAccount.class, accId);
        if (account == null) throw new Exception(ExceptionDefines.ACCOUNT_NOTFOUND);
        
        return GatewayBridge.getBalance(account);
    }

    @Override
    public MobileCardDepositeItem deposite(Long accId, MobileCardDepositeItem item) throws Exception {
        // get account
        TblAccount account = em.find(TblAccount.class, accId);
        if (account == null) throw new Exception(ExceptionDefines.ACCOUNT_NOTFOUND);
        
        return CardDepositeBridge.deposite(account, item);
    }

    @Override
    public AccountInforItem getAccountInfor(Long accId) throws Exception {
        // get account
        TblAccount account = em.find(TblAccount.class, accId);
        if (account == null) throw new Exception(ExceptionDefines.ACCOUNT_NOTFOUND);
        
        AccountInforItem ret = new AccountInforItem();
        ret.setMobile(account.getMobile());
        ret.setAddress(account.getAddress());
        return ret;
    }

    @Override
    public void setAccountInfor(Long accId, AccountInforItem infor) throws Exception {
        // get account
        TblAccount account = em.find(TblAccount.class, accId);
        if (account == null) throw new Exception(ExceptionDefines.ACCOUNT_NOTFOUND);
        
        account.setMobile(infor.getMobile());
        account.setAddress(infor.getAddress());
        em.merge(account);
    }
}
