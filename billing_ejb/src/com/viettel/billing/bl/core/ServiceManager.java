package com.viettel.billing.bl.core;

import java.util.List;
import javax.persistence.EntityManager;

public class ServiceManager {
    /*
    public static TblService findService_ByName(String name, EntityManager em) {
        List<TblService> lst = em.createNamedQuery("TblService.findByName", TblService.class)
            .setParameter("name", name.toLowerCase())
            .setMaxResults(1).getResultList();
        return (lst.size() > 0 ? lst.get(0) : null);
    }
*/
}
