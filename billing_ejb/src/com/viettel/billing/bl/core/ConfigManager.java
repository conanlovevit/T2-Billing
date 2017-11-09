package com.viettel.billing.bl.core;

import com.viettel.billing.bl.database.TblConfig;

import com.viettel.billing.publicItem.consts.ExceptionDefines;

import java.util.List;

import javax.persistence.EntityManager;

public class ConfigManager {
    public static TblConfig getConfig(String key, EntityManager em) {
        List<TblConfig> lst = em.createNamedQuery("TblConfig.findByKey", TblConfig.class)
            .setParameter("key", key.toLowerCase())
            .setMaxResults(1).getResultList();
        return (lst.size() > 0 ? lst.get(0) : null);
    }
    
    public static long getLoginTimeoutMiliseconds(EntityManager em) throws Exception {
        TblConfig config = getConfig("LOGIN_TIMEOUT", em);
        if (config == null || config.getValueNumber() == null) throw new Exception(ExceptionDefines.CONFIG_NOTFOUND);
        return config.getValueNumber() * 60 * 1000;
    }
    
    public static String getAppImageRoot(EntityManager em) {
        TblConfig config = getConfig("APP_IMAGE_ROOT", em);
        return config == null ? null : config.getValueString();
    }
}
