package com.viettel.billing.api;


import com.viettel.billing.publicItem.CCAccount;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface CustomerCare {
    List<CCAccount> findAccount(String device, int start, int limit);
    void blockAccount(long id, boolean isBlock) throws Exception;
    
    void activeDevice(String device) throws Exception;
}
