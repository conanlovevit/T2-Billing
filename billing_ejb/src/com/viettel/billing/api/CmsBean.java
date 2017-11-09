package com.viettel.billing.api;

import com.viettel.billing.publicItem.ActiveItem;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface CmsBean {
    public List<ActiveItem> reportActive(String fromDate, String toDate) throws Exception;
    /*
    // return new Id of Package
    Long createPackage(PackageItem packageItem) throws Exception;
    
    PackageItem getPackageById(Long id);
    void deletePackage(Long id) throws Exception;
    
    // edit info of package: price, duration, autoRebuy, extendMobile, extendStb, active, startDate, endDate
    PackageItem editPackage(PackageItem packageItem) throws Exception;
    
    // promotion
    List<PackagePromotionItem> getPromotionByPackage(long packageId) throws Exception;
    PackagePromotionItem createPromotion(PackagePromotionItem item) throws Exception;
    PackagePromotionItem editPromotion(PackagePromotionItem item) throws Exception;
    PackagePromotionItem getPromotionById(long promotionId) throws Exception;
    void removePromotion(long promotionId);
    
    // edit info of packageService: freeNumber, Percent
    PackageServiceItem editPackageService(PackageServiceItem item) throws Exception;

    // return new Id of PackageServiceItem
    Long addPackageService(Long packageId, PackageServiceItem item) throws Exception;    
    
    List<PackageItem> getActivePackageList();
    List<PackageItem> getBlockPackageList();
    List<PackageItem> getAllPackageList();
    
    void createDevice(String deviceId);*/
}
