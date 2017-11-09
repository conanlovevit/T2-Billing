package com.lbv4.bl;

import com.lbv4.api.LoadBalanceApi;

import com.lbv4.bl.cdn.CdnInterface;

import com.lbv4.bl.cdn.CdnViet;

import com.lbv4.bl.cdn.CdnViet_v2;
import com.lbv4.bl.cdn.CdnViettel;
import com.lbv4.bl.cdn.CdnVtcmedia;

import com.lbv4.utils.LoadbalanceConfig;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

import javax.annotation.Resource;

import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "LoadBalanceApi", mappedName = "LoadBalanceApi")
public class LoadBalanceApiBean implements LoadBalanceApi {
    @Resource
    SessionContext sessionContext;

    @Override
    public String getStreamingUrl(String ip, String path, String cdnName) throws Exception {
        CdnInterface obj = null;
        if (LoadbalanceConfig._CDN_VIET_.equalsIgnoreCase(cdnName)) obj = new CdnViet_v2();
        if (LoadbalanceConfig._CDN_VIETTEL_.equalsIgnoreCase(cdnName)) obj = new CdnViettel();
        if (obj == null) obj = new CdnVtcmedia();
        
        return obj.getStream(ip, path);
    }

}
