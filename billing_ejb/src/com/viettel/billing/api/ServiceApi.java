package com.viettel.billing.api;

import com.viettel.billing.publicItem.ContentItem;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ServiceApi {
    boolean checkPlay(Long accId) throws Exception;
}
