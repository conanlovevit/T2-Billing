package com.idc.launcher;

import com.idc.launcher.items.ItemAppVersion;
import com.idc.launcher.items.ItemConfig;
import com.idc.launcher.items.ItemDevice;
import com.idc.launcher.items.ItemItem;
import com.idc.launcher.items.ItemLauncher;

import com.idc.launcher.items.ItemNotice;
import com.idc.launcher.items.ItemPackageIcon;
import com.idc.launcher.items.ItemType;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface IdcLauncherEjb {
    ItemAppVersion getNewestAppVersion();
    
    List<ItemLauncher> getItemsFindAll(String deviceId);
    List<ItemItem> getItemsFindByParent(Long parentId) throws Exception;
    ItemItem getItemById(Long id);
    void removeItem(Long id) throws Exception;
    
    ItemItem editItem(ItemItem item) throws Exception;
    
    ItemItem createItem(ItemItem item, Long parentId, Long typeId) throws Exception;

    List<ItemType> getTypeList();

    void createOrUpdateConfig(String key, String value);

    List<ItemConfig> getConfigFindAll();

    List<ItemPackageIcon> getAllPackageIcon();

    String getConfigFindByName(String name);
    
    List<ItemNotice> getActiveNotice();
    List<ItemNotice> getNoticeList(int start, int numbet, boolean isOnlyActive);
    ItemNotice createNotice(ItemNotice item) throws Exception;
    ItemNotice editNotice(ItemNotice item) throws Exception;
    void removeNotice(Long id) throws Exception;
    ItemNotice getNoticeById(Long id) throws Exception;
    
    // ************************ NEW 4 CMS ************************ //
    // device
    List<ItemDevice> getDeviceList();
    ItemDevice getDeviceById(Long id);
    ItemDevice createDevice(ItemDevice item);
    ItemDevice editDevice(ItemDevice item) throws Exception;
    void deleteDevice(Long id) throws Exception;
    
    // get Item by device ID
    List<ItemItem> getItemListByDevice(Long deviceId) throws Exception;
}
