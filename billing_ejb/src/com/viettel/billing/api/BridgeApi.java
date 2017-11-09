package com.viettel.billing.api;

import com.viettel.billing.publicItem.AccountInforItem;
import com.viettel.billing.publicItem.AccountItem;

import com.viettel.billing.publicItem.MobileCardDepositeItem;
import com.viettel.billing.publicItem.PackageItem;

import com.viettel.billing.publicItem.applicationItem;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface BridgeApi {
    boolean checkActive(String deviceCode) throws Exception;    
    void activeDevice(String deviceCode, String sign) throws Exception;
    void createDevice(String deviceCode, String mac1, String mac2, String deviceModelCode) throws Exception;
    void importZ5(String deviceModel, String deviceCode,String  account,String  account_id,String  activeDate,String expireDate,String balance) throws Exception;
    
    List<applicationItem> getAppByVersion(String version);

    AccountItem login(String deviceCode, String time, String macSign) throws Exception;
    List<PackageItem> getPackage(Long accId) throws Exception;
    PackageItem buyPackage(Long accId, Long packageId) throws Exception;
    void removePackage(Long accId, Long packageAccountId) throws Exception;

    long getBalance(Long accId) throws Exception;   
    
    MobileCardDepositeItem deposite(Long accId, MobileCardDepositeItem item) throws Exception;
    
    AccountInforItem getAccountInfor(Long accId) throws Exception;
    void setAccountInfor(Long accId, AccountInforItem infor) throws Exception;
}
