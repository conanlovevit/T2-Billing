package com.viettel.billing.bridges;

import com.viettel.billing.bl.database.TblAccount;
import com.viettel.billing.publicItem.MobileCardDepositeItem;
import com.viettel.billing.publicItem.consts.ExceptionDefines;
import com.viettel.billing.utils.HttpClientManager;

import com.viettel.billing_log.api.BillingLog;

import com.vtce.remote.attribute.Jndi;
import com.vtce.service.CheckCardRemote;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CardDepositeBridge {
    //    private final static String _URL_ = "https://paygate.kenhitv.vn/XMIOCardService/webresources/CardService/ActiveCard";
//    private final static String _URL_ = "http://123.30.145.220/XMService/webresources/CardService/ActiveCard";
//    private final static String _URL_ = "http://apit2.ztv.vn/XMService/webresources/CardService/ActiveCard";
    //    private final static String _URL_ = "http://125.212.227.225:8080/XMService/webresources/CardService/ActiveCard";
        
        private final static String userNameLogin = "vtce";
        private final static String passwodLogin = "vtce@2015";

        public static MobileCardDepositeItem deposite(TblAccount account,
                                                      MobileCardDepositeItem depositeItem) throws Exception {
            CheckCardRemote ejb = getEjb();
            String json = ejb.checkCard(String.valueOf(account.getId()), account.getAccountId(), 
                          depositeItem.getSerial(), depositeItem.getCode(), 
                          depositeItem.getCardType(), 
                          userNameLogin, passwodLogin);
            JSONParser parser = new JSONParser();
            JSONObject ret = (JSONObject) parser.parse(json);
            
            if (ret == null) throw new Exception("Mobile Card Deposite System error!");
            
            long status = (Long) ret.get("code");
            if (status == 1l) {
                depositeItem.setMoney(Long.parseLong(ret.get("amount").toString()));
                return depositeItem;
            } else {
                throw new Exception(ExceptionDefines.CARD_FAILED + (String) ret.get("description"));
            }
        }

    private static CheckCardRemote getEjb() throws Exception {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);            
    
        return (CheckCardRemote) context.lookup(Jndi.CheckCard_BEAN_REMOTE_JBOSS7);
    }

        public static void main(String[] args) throws Exception {
            long time =System.currentTimeMillis();
            TblAccount account = new TblAccount();
            account.setId(123l);
            account.setAccountId("test");
            
            try {
                MobileCardDepositeItem depositeItem = new MobileCardDepositeItem();
                depositeItem.setCardType(MobileCardDepositeItem.TYPE_VIETTEL);
                depositeItem.setCode("23r4");
                depositeItem.setSerial("trtgt");
                deposite(account, depositeItem);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println(System.currentTimeMillis() - time);
        }
}
