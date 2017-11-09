package com.idc.launcher;

import com.idc.launcher.database.AppVersion;
import com.idc.launcher.database.Config;
import com.idc.launcher.database.Device;
import com.idc.launcher.database.Items;
import com.idc.launcher.database.Notice;
import com.idc.launcher.database.NoticeType;
import com.idc.launcher.database.PackageIcon;
import com.idc.launcher.database.Type;

import com.idc.launcher.items.ItemAppVersion;
import com.idc.launcher.items.ItemConfig;

import com.idc.launcher.items.ItemDevice;
import com.idc.launcher.items.ItemItem;
import com.idc.launcher.items.ItemLauncher;

import com.idc.launcher.items.ItemNotice;
import com.idc.launcher.items.ItemPackageIcon;
import com.idc.launcher.items.ItemType;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(name = "IdcLauncherEjb", mappedName = "IdcLauncherEjb")
public class IdcLauncherEjbBean implements IdcLauncherEjb {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "LauncherEjb")
    private EntityManager em;

    public IdcLauncherEjbBean() {
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object queryByRange(String jpqlStmt, int firstResult, int maxResults) {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }

    /** <code>select o from Type o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Type> getTypeFindAll() {
        return em.createNamedQuery("Type.findAll", Type.class).getResultList();
    }
    
    private ItemLauncher itemLauncherConvert(Items item) {
        if (item == null) return null;
        ItemLauncher ret = new ItemLauncher();
        
        ret.setId(item.getId());
        if (item.getParent() != null) ret.setParentId(item.getParent().getId());
        ret.setCode(item.getCode());
        ret.setIcon(item.getIcon());
        ret.setImage(item.getImage());
        ret.setName(item.getName());
        ret.setPackageId(item.getPackageId());
        ret.setPos(item.getPos());
        ret.setType(item.getType().getName());
        ret.setUrl(item.getUrl());
        ret.setLastUpdate(item.getLastUpdate());
        
        return ret;
    }

    /** <code>select o from Device o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public long getDevice(String deviceId) {
        List<Device> lst =  em.createNamedQuery("Device.findAll", Device.class).getResultList();
//        System.out.println(lst.size());
        for (int i = 0; i < lst.size(); i++) {
//            System.out.println(i + " -> " + lst.get(i).getDeviceIdStart());
            if (lst.get(i).getDeviceIdStart() != null && deviceId.toLowerCase().startsWith(lst.get(i).getDeviceIdStart().toLowerCase())) return lst.get(i).getId();
        }
        return 0; // default
    }

    /** <code>select o from Items o order by o.pos</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ItemLauncher> getItemsFindAll(String deviceId) {
//        System.out.println("deviceId -> " + deviceId);
        long device = 0;
        if (deviceId != null) device = getDevice(deviceId);
//        System.out.println("device -> " + device);
        List<Items> lst = em.createNamedQuery("Items.findByDevice", Items.class).setParameter("deviceId", device) .getResultList();;
        
        // gen map data
        Map<Long, ItemLauncher> mapData = new HashMap<Long, ItemLauncher>();
        for (int i = 0; i < lst.size(); i++) {
            mapData.put(lst.get(i).getId(), itemLauncherConvert(lst.get(i)));
        }
        
        // gen result
        List<ItemLauncher> ret = new ArrayList<ItemLauncher>();
        for (int i = 0; i < lst.size(); i++) {
            ItemLauncher item = mapData.get(lst.get(i).getId());
            if (item.getParentId() != null) {
                mapData.get(item.getParentId()).addChild(item);
            } else {
                ret.add(item);
            }
        }
        return ret;
    }

    /** <code>select o from Config o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ItemConfig> getConfigFindAll() {
        List<Config> lst =  em.createNamedQuery("Config.findAll", Config.class).getResultList();
        List<ItemConfig> ret = new ArrayList<ItemConfig>();
        for (int i = 0; i < lst.size(); i++) {
            ret.add(new ItemConfig(lst.get(i).getName(), lst.get(i).getValue()));
        }
        return ret;
    }

    /** <code>select o from Config o where UPPER(name) like :name</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getConfigFindByName(String name) {
        List<Config> lst = em.createNamedQuery("Config.findByName", Config.class).setParameter("name", name.toUpperCase()).getResultList();
        return (lst.size() > 0 ? lst.get(0).getValue() : null);
    }

    @Override
    public void createOrUpdateConfig(String key, String value) {
        List<Config> lst =
            em.createNamedQuery("Config.findByName", Config.class).setParameter("name",
                                                                                key.toUpperCase()).getResultList();
        if (lst.size() > 0) {
            // update
            Config cfg = lst.get(0);
            cfg.setValue(value);
            em.merge(cfg);
        } else {
            // create
            Config cfg = new Config();
            cfg.setName(key);
            cfg.setValue(value);
            em.persist(cfg);
        }
    }

    @Override
    public List<ItemType> getTypeList() {
        List<Type> lst = getTypeFindAll();
        List<ItemType> ret = new ArrayList<ItemType>();
        for (int i = 0; i < lst.size(); i++) {
            ret.add(new ItemType(lst.get(i).getId(), lst.get(i).getName()));
        }
        return ret;
    }

    private ItemItem itemConvert(Items item) {
        if (item == null) return null;
        
        ItemItem ret = new ItemItem();
        ret.setCode(item.getCode());
        ret.setIcon(item.getIcon());
        ret.setId(item.getId());
        ret.setImage(item.getImage());
        ret.setName(item.getName());
        ret.setPackageId(item.getPackageId());
        ret.setParent(itemConvert(item.getParent()));
        ret.setPos(item.getPos());
        ret.setType(new ItemType(item.getType().getId(), item.getType().getName()));
        ret.setUrl(item.getUrl());
        ret.setDeviceId(item.getDeviceId());
        return ret;
    }

    private List<ItemItem> itemListConvert(List<Items> lst) {
        List<ItemItem> ret = new ArrayList<ItemItem>();
        if (lst == null) return ret;

        for (int i = 0; i < lst.size(); i++) {
            ret.add(itemConvert(lst.get(i)));
        }
        return ret;
    }

    /** <code>select o from Items o where o.parent = :parent order by o.pos</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<ItemItem> getItemsFindByParent(Long parentId) throws Exception {
        // find parent
        Items parent = null;
        List<Items> lst;
        if (parentId != null) {
            parent = em.find(Items.class, parentId);
            if (parent == null)
                throw new Exception("Error: no Items(id=" + parentId + ") is found!");
            lst = em.createNamedQuery("Items.findByParent", Items.class).setParameter("parent", parent).getResultList();
        } else {
            lst = em.createNamedQuery("Items.findRoot", Items.class).getResultList();
        }
        
        return itemListConvert(lst);
    }

    @Override
    public ItemItem getItemById(Long id) {
        Items item = em.find(Items.class, id);
        
        return itemConvert(item);
    }

    @Override
    public ItemItem editItem(ItemItem item) throws Exception {
        Items obj = em.find(Items.class, item.getId());
        if (obj == null)
            throw new Exception("Error: no Items(id=" + item.getId() + ") is found!");
        
        obj.setCode(item.getCode());
        obj.setIcon(item.getIcon());
        obj.setImage(item.getImage());
        obj.setName(item.getName());
        obj.setPackageId(item.getPackageId());
        obj.setPos(item.getPos());
        obj.setUrl(item.getUrl());
        obj.setDeviceId(item.getDeviceId());
        obj.setLastUpdate(System.currentTimeMillis());
        em.merge(obj);
        
        return itemConvert(obj);
    }

    @Override
    public ItemItem createItem(ItemItem item, Long parentId, Long typeId) throws Exception {
        // find parent
        Items parent = null;
        if (parentId != null) {
            parent = em.find(Items.class, parentId);
            if (parent == null)
                throw new Exception("Error: no Parent(id=" + parentId + ") is found!");
        }
        
        // find type
        Type type = em.find(Type.class, typeId);
        if (type == null)
            throw new Exception("Error: no Type(id=" + typeId + ") is found!");
        
        Items obj = new Items();
        obj.setParent(parent);
        obj.setType(type);
        
        obj.setCode(item.getCode());
        obj.setIcon(item.getIcon());
        obj.setImage(item.getImage());
        obj.setName(item.getName());
        obj.setPackageId(item.getPackageId());
        obj.setPos(item.getPos());
        obj.setUrl(item.getUrl());
        obj.setLastUpdate(System.currentTimeMillis());
        obj.setDeviceId(item.getDeviceId());
        
        em.persist(obj);
        
        return itemConvert(obj);
    }

    @Override
    public void removeItem(Long id) throws Exception {
        Items obj = em.find(Items.class, id);
        if (obj == null)
            throw new Exception("Error: no Items(id=" + id + ") is found!");
        
        if (obj.getType().getName().equalsIgnoreCase("group") && obj.getChildList() != null && obj.getChildList().size() > 0)
            throw new Exception("Error: no Items(id=" + id + ") can not delete!");
        
        em.remove(obj);
    }

    /** <code>select o from Notice o where o.startTime < CURRENT_TIMESTAMP and o.endTime > CURRENT_TIMESTAMP</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Notice> getNoticeFindActive() {
        return em.createNamedQuery("Notice.findActive", Notice.class).getResultList();
    }
    
    /** <code>select o from Notice o where o.startTime < CURRENT_TIMESTAMP and o.endTime > CURRENT_TIMESTAMP</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Notice> getNoticeFindActive(int start, int number) {
        return em.createNamedQuery("Notice.findActive", Notice.class).setFirstResult(start).setMaxResults(number).getResultList();
    }
    
    /** <code>select o from Notice o order by o.id desc</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Notice> getNoticeFindAll(int start, int number) {
        return em.createNamedQuery("Notice.findAll", Notice.class).setFirstResult(start).setMaxResults(number).getResultList();
    }
    
    private ItemNotice convertNotice(Notice item) {
        if (item == null) return null;
        ItemNotice ret = new ItemNotice();
        ret.setContent(item.getContent());
        ret.setEnd_time(item.getEndTime());
        ret.setFrequency(item.getFrequency());
        ret.setDuration(item.getDuration());
        ret.setId(item.getId());
        ret.setImage(item.getImage());
        ret.setStart_time(item.getStartTime());
        ret.setTitle(item.getTitle());
        ret.setType(item.getNoticeType() != null && item.getNoticeType().getName().equalsIgnoreCase("image") ? ItemNotice.TYPE_IMAGE : ItemNotice.TYPE_TEXT);
        
        return ret;
    }
    
    private List<ItemNotice> convertNoticeList(List<Notice> item) {
        List<ItemNotice> lst = new ArrayList<ItemNotice>();
        if (item != null) {
            for (int i = 0; i < item.size(); i++) {
                lst.add(convertNotice(item.get(i)));
            }
        }
        return lst;
    }
    
    @Override
    public List<ItemNotice> getActiveNotice() {
        return convertNoticeList(getNoticeFindActive());
    }

    @Override
    public List<ItemNotice> getNoticeList(int start, int number, boolean isOnlyActive) {
        if (isOnlyActive) {
            return convertNoticeList(getNoticeFindActive(start, number));
        } else {
            return convertNoticeList(getNoticeFindAll(start, number));
        }
    }

    @Override
    public ItemNotice createNotice(ItemNotice item) throws Exception {
        // find NoticeType
        NoticeType type = em.find(NoticeType.class, new Long(item.getType()));
        if (type == null) throw new Exception("Error: no type found!");
        
        Notice obj = new Notice();
        obj.setNoticeType(type);
        obj.setTitle(item.getTitle());
        obj.setContent(item.getContent());
        obj.setImage(item.getImage());
        obj.setFrequency(item.getFrequency());
        obj.setDuration(item.getDuration());

        obj.setStartTime(item.getStart_time());
        obj.setEndTime(item.getEnd_time());

        obj.setCreateTime(new Timestamp(System.currentTimeMillis()));
        
        em.persist(obj);
        
        return convertNotice(obj);
    }

    @Override
    public ItemNotice editNotice(ItemNotice item) throws Exception {
        // find Notice
        Notice obj = em.find(Notice.class, item.getId());
        if (obj == null) throw new Exception("Error: no notice found!");
        
        // find NoticeType
        NoticeType type = em.find(NoticeType.class, new Long(item.getType()));
        if (type == null) throw new Exception("Error: no type found!");
        
        obj.setNoticeType(type);
        obj.setTitle(item.getTitle());
        obj.setContent(item.getContent());
        obj.setImage(item.getImage());
        obj.setFrequency(item.getFrequency());
        obj.setDuration(item.getDuration());

        obj.setStartTime(item.getStart_time());
        obj.setEndTime(item.getEnd_time());

        em.merge(obj);
        
        return convertNotice(obj);
    }

    @Override
    public void removeNotice(Long id) throws Exception {
        // find Notice
        Notice obj = em.find(Notice.class, id);
        if (obj == null) throw new Exception("Error: no notice found!");
        
        em.remove(obj);
    }

    @Override
    public ItemNotice getNoticeById(Long id) throws Exception {
        // find Notice
        Notice obj = em.find(Notice.class, id);
        if (obj == null) throw new Exception("Error: no notice found!");

        return convertNotice(obj);
    }

    @Override
    public List<ItemPackageIcon> getAllPackageIcon() {
        List<PackageIcon> lst = em.createNamedQuery("PackageIcon.findAll", PackageIcon.class).getResultList();    
        List<ItemPackageIcon> ret = new ArrayList<ItemPackageIcon>();
        for (int i = 0; i < lst.size(); i++) {
            ItemPackageIcon item = new ItemPackageIcon();
            item.setAppName(lst.get(i).getAppName());
            item.setIcon(lst.get(i).getIcon());
            item.setId(lst.get(i).getId());
            item.setPackageName(lst.get(i).getPackageName());
            ret.add(item);
        }
        
        return ret;
    }

    @Override
    public List<ItemDevice> getDeviceList() {
        List<Device> lst = em.createNamedQuery("Device.findAll", Device.class).getResultList();   
        List<ItemDevice> ret = new ArrayList<ItemDevice>();
        for (int i = 0; i < lst.size(); i++) {
            ItemDevice item = new ItemDevice();
            item.setId(lst.get(i).getId());
            item.setDeviceIdStart(lst.get(i).getDeviceIdStart());
            item.setName(lst.get(i).getName());
            ret.add(item);
        }
        
        return ret;
    }

    @Override
    public ItemDevice getDeviceById(Long id) {
        Device obj = em.find(Device.class, id);
        if (obj == null) return null;
        
        ItemDevice item = new ItemDevice();
        item.setId(obj.getId());
        item.setDeviceIdStart(obj.getDeviceIdStart());
        item.setName(obj.getName());
        
        return item;
    }

    @Override
    public ItemDevice editDevice(ItemDevice item) throws Exception {
        Device obj = em.find(Device.class, item.getId());
        if (obj == null) throw new Exception("Error: no Device found!");
        
        obj.setDeviceIdStart(item.getDeviceIdStart());
        obj.setName(item.getName());
        em.merge(obj);
        return item;
    }

    @Override
    public void deleteDevice(Long id) throws Exception {
        Device device = em.find(Device.class, id);
        if (device == null) throw new Exception("Error: no Device found!");
        
        List<Items> lst = em.createNamedQuery("Items.findByDevice", Items.class).setParameter("deviceId", device) .getResultList();
        if (lst.size() > 0) throw new Exception("Error: can't delete device!");
        
        em.remove(device);
    }

    @Override
    public List<ItemItem> getItemListByDevice(Long deviceId) throws Exception {
        Device device = em.find(Device.class, deviceId);
        if (device == null) throw new Exception("Error: no Device found!");
        List<Items> lst = em.createNamedQuery("Items.findByDevice", Items.class).setParameter("deviceId", deviceId) .getResultList();
        return itemListConvert(lst);
    }

    @Override
    public ItemDevice createDevice(ItemDevice item) {
        Device device = new Device();
        device.setDeviceIdStart(item.getDeviceIdStart());
        device.setName(item.getName());
        em.persist(device);
        
        item.setId(device.getId());
        return item;
    }

    @Override
    public ItemAppVersion getNewestAppVersion() {
        List<AppVersion> lst = em.createNamedQuery("AppVersion.findNewst", AppVersion.class).setMaxResults(1).getResultList();
        if (lst == null || lst.size() <= 0) return null;
        
        AppVersion obj = lst.get(0);
        ItemAppVersion ret = new ItemAppVersion();
        ret.setActive(obj.getStatus().intValue() == 1);
        ret.setChangeLog(obj.getChangeLog());
        ret.setDatetime(obj.getDatetime());
        ret.setDownloadUrl(obj.getDownloadUrl());
        ret.setFileSize(obj.getFileSize());
        ret.setForced(obj.getForced().intValue() == 1);
        ret.setId(obj.getId());
        ret.setMd5Hash(obj.getMd5Hash());
        ret.setMessage(obj.getMessage());
        ret.setName(obj.getName());
        ret.setPackageName(obj.getPackageName());
        ret.setVersionCode(obj.getVersionCode());
        ret.setVersionName(obj.getVersionName());
        ret.setVersionNumber(obj.getVersionNumber());
        return ret;
    }
}
