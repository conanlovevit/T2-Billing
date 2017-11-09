package com.viettel.billing.bridges;

import com.viettel.billing.bl.database.TblAccount;
import com.viettel.billing.bl.database.TblPackage;
import com.viettel.billing.publicItem.ContentItem;

import com.viettel.billing.publicItem.consts.ExceptionDefines;
import com.viettel.billing.publicItem.consts.ServiceDefines;
import com.viettel.billing.utils.HttpClientManager;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJBException;

import org.json.simple.JSONObject;

public class GatewayBridge {
    private static class Respond {
        public final static long ERROR_NOT_ENOUGH_MONEY = 105;

        public long status;
        public Object data;
        public long errorCode;
        public String errorMessage;

        public boolean isError() {
            return status < 0;
        }

        public static Respond parseRespond(JSONObject obj) {
//            System.out.println("parsePaygateRespond: " + obj.toString());
            
            Respond ret = new Respond(); 
            ret.status = Long.parseLong((String) obj.get("status"));
            if (ret.status >= 0) {
                ret.data = obj.get("data");
            } else {
                JSONObject errorObj = (JSONObject) obj.get("error");
                ret.errorCode = Long.parseLong((String) errorObj.get("code"));
                ret.errorMessage = (String) errorObj.get("message");
            }
            return ret;
        }
    }

        //live
        private final static String _PAYGATE_URL_ = "http://192.168.75.5:8080/paygate_service/service/";
        //test
        //private final static String _PAYGATE_URL_ = "http://10.10.10.56:8080/paygate_service/service/";
        
    private final static String _XMIO_USERNAME_ = "xmio";
    private final static String _XMIO_PASSWORD_ = "xmio";

    private final static String DEPOSIT_CUSTOMER_CARE = "DPS_CUSTOMER_CARE";
    private final static String PAYMENT_CUSTOMER_CARE = "PAYMENT_CUSTOMER_CARE";
    
    private final static String PAYMENT_PPM = "PAYMENT_PPM";

    private final static String _METHOD_CDR_ = "cdr/create";
    private final static String _METHOD_DEPOSIT_ = "account/deposit";
    private final static String _METHOD_PAYMENT_ = "account/payment";
    private final static String _METHOD_CREATEACCOUNT_ = "account/create";
    private final static String _METHOD_BALANCE_ = "account/balance";
    private final static String _METHOD_TRANSFER_ = "account/transfer";
    private final static String _METHOD_CHANGEUSERNAME_ = "account/change_username";

    private static Map<String, String> genDefaultParam() {
        Map<String, String> lParam = new HashMap<String, String>();
        lParam.put("partner", _XMIO_USERNAME_);
        lParam.put("password", _XMIO_PASSWORD_);
        return lParam;
    }
    
    public static void ppm(TblAccount account, String packageName, Long price) throws Exception {
        Map<String, String> lParam = genDefaultParam();
        lParam.put("username", account.getAccountId());
        lParam.put("amount", String.valueOf(price));
        lParam.put("service_code", PAYMENT_PPM);
        lParam.put("detail", "Mua gói trả trước: " + packageName);
        Respond respond =
            Respond.parseRespond(HttpClientManager.sendHttpPostRequest_json(_PAYGATE_URL_ + _METHOD_PAYMENT_,
                                                                            lParam));
        if (respond.isError()) {
            if (respond.errorCode == Respond.ERROR_NOT_ENOUGH_MONEY)
                throw new Exception(ExceptionDefines.PGT_NOT_ENOUGH_MONEY);
            throw new Exception(respond.errorCode + ":" + respond.errorMessage);
        }
    }
    
    public static void createAccount(String accountId) throws Exception {
        Map<String, String> lParam = genDefaultParam();
        lParam.put("username", accountId);
        Respond respond =
            Respond.parseRespond(HttpClientManager.sendHttpPostRequest_json(_PAYGATE_URL_ + _METHOD_CREATEACCOUNT_,
                                                                            lParam));
        System.out.println("response_code:"+respond.errorCode);
        if (respond.isError()) throw new EJBException(respond.errorMessage);
    }

    public static long getBalance(TblAccount account) throws Exception {
        Map<String, String> lParam = genDefaultParam();
        lParam.put("username", account.getAccountId());
        Respond respond =
            Respond.parseRespond(HttpClientManager.sendHttpPostRequest_json(_PAYGATE_URL_ + _METHOD_BALANCE_, lParam));
        
        if (respond.isError())
            throw new Exception(respond.errorMessage);

        return ((Double) respond.data).longValue();
    }
    public static void Z5_addMoney(String username, long money) throws Exception {
        
        Map<String, String> lParam = genDefaultParam();
        lParam.put("username", username);
        lParam.put("amount", String.valueOf(money));
        lParam.put("service_code", DEPOSIT_CUSTOMER_CARE);
        lParam.put("detail", "Nạp tiền chuyển đổi Z5 -> T2!");
       
       
        Respond respond =
            Respond.parseRespond(HttpClientManager.sendHttpPostRequest_json(_PAYGATE_URL_ + _METHOD_DEPOSIT_,
                                                                            lParam));
        if (respond.isError()) {
            throw new Exception(respond.errorCode + ":" + respond.errorMessage);
        }
    }
    
    public static void main(String[] args) throws Exception {
//        createAccount("cnrubftr");
        TblAccount account = new TblAccount();
        account.setAccountId("00000012_1449140169780");
        System.out.println(getBalance(account));
        TblAccount acc = new TblAccount();
        acc.setAccountId("00000012_1449140169780");
        ppm(acc, "ztv 1", 0L);
    }
}
