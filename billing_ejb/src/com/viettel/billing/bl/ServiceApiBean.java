package com.viettel.billing.bl;

import com.viettel.billing.api.ServiceApi;

import com.viettel.billing.bl.core.AccountManager;
import com.viettel.billing.bl.core.PackageManager;
import com.viettel.billing.bl.core.PpmManager;
import com.viettel.billing.bl.core.PpvManager;
import com.viettel.billing.bl.core.ServiceManager;
import com.viettel.billing.bridges.GatewayBridge;
import com.viettel.billing.publicItem.ContentItem;

import com.viettel.billing.publicItem.consts.AccountStatusDefines;
import com.viettel.billing.publicItem.consts.ExceptionDefines;

import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "ServiceApi", mappedName = "ServiceApi")
public class ServiceApiBean implements ServiceApi {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "billing_ejb")
    private EntityManager em;

    @Override
    public boolean checkPlay(Long accId) throws Exception {
        return PpmManager.isPlay(accId, em);
    }
}
