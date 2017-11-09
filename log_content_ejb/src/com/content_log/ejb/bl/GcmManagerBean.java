package com.content_log.ejb.bl;

import com.content_log.ejb.api.GcmManager;

import com.content_log.ejb.bl.model.TblGcm;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.util.List;

import javax.annotation.Resource;

import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(name = "GcmManager", mappedName = "GcmManager")
public class GcmManagerBean implements GcmManager {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "log_content_ejb")
    private EntityManager em;

    public GcmManagerBean() {
    }

    /** <code>select o from TblGcm o where o.lastUpdate >= :yesterday</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<TblGcm> getTblGcmFindActive(Timestamp yesterday) {
        Query q = em.createNativeQuery("select seq_gcm_lock.nextval from dual");
        BigDecimal number = (BigDecimal) q.getSingleResult();
        if (number.longValue() % 2 == 0) return null;
        
        return em.createNamedQuery("TblGcm.findActive", TblGcm.class).setParameter("yesterday", yesterday).getResultList();
    }
}
