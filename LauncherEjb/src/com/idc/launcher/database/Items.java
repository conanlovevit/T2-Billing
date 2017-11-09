package com.idc.launcher.database;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({ @NamedQuery(name = "Items.findAll", query = "select o from Items o order by o.pos"),
                @NamedQuery(name = "Items.findByDevice", query = "select o from Items o where o.deviceId = :deviceId order by o.pos"),
                @NamedQuery(name = "Items.findByParent", query = "select o from Items o where o.parent = :parent order by o.pos"),
                @NamedQuery(name = "Items.findRoot", query = "select o from Items o where o.parent is null order by o.pos")
                })
@Cacheable(false)
public class Items {
    @Column(length = 20)
    private String code;
    @Column(length = 200)
    private String icon;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", 
                       sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
                    generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, name = "DEVICE_ID")
    private Long deviceId;
    @Column(length = 200)
    private String image;
    @Column(nullable = false, length = 50)
    private String name;
    private Integer pos;
    @Column(name = "PACKAGE_ID", length = 200)
    private String packageId;    
    @Column(length = 100)
    private String url;
    @ManyToOne
    @JoinColumn(name = "TYPE_ID")
    private com.idc.launcher.database.Type type;
    @ManyToOne
    @JoinColumn(name = "PARENT")
    private Items parent;
    @OneToMany(mappedBy = "parent", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Items> childList;
    @Column(name = "LAST_UPDATE", nullable = false)
    private Long lastUpdate;

    public Items() {
    }

    public Items(String code, String icon, Long id, String image, String name, Items items, Integer pos,
                 com.idc.launcher.database.Type type,
                 String url) {
        this.code = code;
        this.icon = icon;
        this.id = id;
        this.image = image;
        this.name = name;
        this.parent = items;
        this.pos = pos;
        this.type = type;
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public void setPackageId(String packageName) {
        this.packageId = packageName;
    }

    public String getPackageId() {
        return packageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public com.idc.launcher.database.Type getType() {
        return type;
    }

    public void setType(com.idc.launcher.database.Type type) {
        this.type = type;
    }

    public Items getParent() {
        return parent;
    }

    public void setParent(Items items) {
        this.parent = items;
    }

    public List<Items> getChildList() {
        return childList;
    }

    public void setChildList(List<Items> childList) {
        this.childList = childList;
    }

    public Items addItems(Items items) {
        getChildList().add(items);
        items.setParent(this);
        return items;
    }

    public Items removeItems(Items items) {
        getChildList().remove(items);
        items.setParent(null);
        return items;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }
}
