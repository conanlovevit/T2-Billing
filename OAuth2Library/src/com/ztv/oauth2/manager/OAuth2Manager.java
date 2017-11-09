package com.ztv.oauth2.manager;


import com.ztv.oauth2.AccountInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class OAuth2Manager {
    private static String _PRIVATE_KEY_ = "nruitbvBERUIBFRvrvtCCJHDFVJ";
    private static String _REFRESH_KEY_ = "cbruICEB58495y932frnFIEB238FRBreurbcJHB";

    public static JSONObject genHeader() {
        JSONObject obj = new JSONObject();
        obj.put(Config._TYPE_NAME_, Config._TYPE_VALUE_);
        obj.put(Config._ALGORITHM_NAME_, Config._ALGORITHM_VALUE_);
        return obj;
    }
    

    public static JSONObject genClaims(AccountInfo accountInfo, List<String> scopes) {
        JSONObject obj = new JSONObject();

        JSONObject objAccount = new JSONObject();
        objAccount.put(Config._ACC_ID_NAME_, accountInfo.getId());
        objAccount.put(Config._ACC_USERNAME_NAME_, accountInfo.getAccountId());
        objAccount.put(Config._ACC_DEVICE_ID_, accountInfo.getDeviceId());
        objAccount.put(Config._ACC_DEVICE_TYPE_, accountInfo.getDeviceType());
        objAccount.put(Config._ACC_DEVICE_OS_, accountInfo.getOs());
        objAccount.put(Config._ACC_LOCATION_CODE_, accountInfo.getLocationCode());

        obj.put(Config._ACCOUNT_NAME_, objAccount);

        //        JSONArray list = new JSONArray();
        //        for (int i = 0; i < scopes.size(); i++) {
        //            list.add(scopes.get(i));
        //        }
        //        obj.put(Config._SCOPES_NAME_, list);

        obj.put(Config._TIMEOUT_NAME_, String.valueOf(System.currentTimeMillis() + Config._TIMEOUT_MINISECOND_));
        obj.put(Config._REFRESH_TIMEOUT_NAME_,
                String.valueOf(System.currentTimeMillis() + Config._REFRESH_TIMEOUT_MINISECOND_));

        return obj;
    }

    public static JSONObject genRefreshClaims(AccountInfo accountInfo, List<String> scopes, String refreshTimeout) {
        JSONObject obj = new JSONObject();

        JSONObject objAccount = new JSONObject();
        objAccount.put(Config._ACC_ID_NAME_, accountInfo.getId());
        objAccount.put(Config._ACC_USERNAME_NAME_, accountInfo.getAccountId());
        objAccount.put(Config._ACC_DEVICE_ID_, accountInfo.getDeviceId());
        objAccount.put(Config._ACC_DEVICE_TYPE_, accountInfo.getDeviceType());
        objAccount.put(Config._ACC_DEVICE_OS_, accountInfo.getOs());
        objAccount.put(Config._ACC_LOCATION_CODE_, accountInfo.getLocationCode());

        obj.put(Config._ACCOUNT_NAME_, objAccount);

        //        JSONArray list = new JSONArray();
        //        for (int i = 0; i < scopes.size(); i++) {
        //            list.add(scopes.get(i));
        //        }
        //        obj.put(Config._SCOPES_NAME_, list);

        obj.put(Config._TIMEOUT_NAME_, String.valueOf(System.currentTimeMillis() + Config._TIMEOUT_MINISECOND_));
        obj.put(Config._REFRESH_TIMEOUT_NAME_, refreshTimeout);

        return obj;
    }

    public static String genSignature(JSONObject json, boolean isRefresh) throws Exception {
        String data = (String) json.get(Config._TIMEOUT_NAME_) + (String) json.get(Config._REFRESH_TIMEOUT_NAME_) +
            getDataFromJson(json, Config._ACCOUNT_NAME_, Config._ACC_ID_NAME_) +
            getDataFromJson(json, Config._ACCOUNT_NAME_, Config._ACC_USERNAME_NAME_) +
            getDataFromJson(json, Config._ACCOUNT_NAME_, Config._ACC_DEVICE_ID_) +
            getDataFromJson(json, Config._ACCOUNT_NAME_, Config._ACC_DEVICE_TYPE_) +
            getDataFromJson(json, Config._ACCOUNT_NAME_, Config._ACC_DEVICE_OS_) +
            getDataFromJson(json, Config._ACCOUNT_NAME_, Config._ACC_LOCATION_CODE_);
        
        return SecurityManager.md5_string(data + (isRefresh ? _REFRESH_KEY_ : _PRIVATE_KEY_)).substring(3, 10);
    }

    public static AccountInfo verifyRefreshToken(JSONObject token) throws Exception {
        if (token.get(Config._REFRESH_SIGNATURE_NAME_) == null)
            return null;

        // verify timeout
        JSONObject claims = (JSONObject) token.get(Config._CLAIMS_NAME_);
        long timeout = Long.parseLong(claims.get(Config._REFRESH_TIMEOUT_NAME_).toString());
        if (System.currentTimeMillis() > timeout)
            return null;

        // verify token
        String tokenStr = token.get(Config._REFRESH_SIGNATURE_NAME_).toString();
        if (!tokenStr.equals(genSignature(claims, true)))
            throw new Exception("Error: failed to verify token!");

        // return
        JSONObject account = (JSONObject) claims.get(Config._ACCOUNT_NAME_);
        AccountInfo ret = new AccountInfo((String) account.get(Config._ACC_ID_NAME_),
                            (String) account.get(Config._ACC_USERNAME_NAME_),
                            (String) account.get(Config._ACC_DEVICE_ID_),
                            (String) account.get(Config._ACC_DEVICE_TYPE_),
                            (String) account.get(Config._ACC_DEVICE_OS_),
                            (String) account.get(Config._ACC_LOCATION_CODE_)
                            );
        return ret;
    }

    public static AccountInfo verifyToken(JSONObject token) throws Exception {
        // verify header
        //        JSONObject header = (JSONObject) token.get(Config._HEADER_NAME_);
        //        if (!Config._TYPE_VALUE_.equals(header.get(Config._TYPE_NAME_).toString()) ||
        //            !Config._ALGORITHM_VALUE_.equals(header.get(Config._ALGORITHM_NAME_).toString()))
        //            throw new Exception("Error: wrong header!");

        // verify timeout
        JSONObject claims = (JSONObject) token.get(Config._CLAIMS_NAME_);
        //        String claims = (String) token.get(Config._CLAIMS_NAME_);
        long timeout = Long.parseLong(claims.get(Config._TIMEOUT_NAME_).toString());
        if (System.currentTimeMillis() > timeout)
            throw new Exception("Error: timeout!");

        // verify token
        String tokenStr = token.get(Config._SIGNATURE_NAME_).toString();
        if (!tokenStr.equals(genSignature(claims, false)))
            throw new Exception("Error: failed to verify token!");
        
        // return
        JSONObject account = (JSONObject) claims.get(Config._ACCOUNT_NAME_);
        
        AccountInfo ret = new AccountInfo((String) account.get(Config._ACC_ID_NAME_),
                            (String) account.get(Config._ACC_USERNAME_NAME_),
                            (String) account.get(Config._ACC_DEVICE_ID_),
                            (String) account.get(Config._ACC_DEVICE_TYPE_),
                            (String) account.get(Config._ACC_DEVICE_OS_),
                            (String) account.get(Config._ACC_LOCATION_CODE_)
                            );
        return ret;
    }

    public static AccountInfo verifyToken_notimeout(JSONObject token) throws Exception {
        // verify timeout
        JSONObject claims = (JSONObject) token.get(Config._CLAIMS_NAME_);

        // verify token
        String tokenStr = token.get(Config._SIGNATURE_NAME_).toString();
        if (!tokenStr.equals(genSignature(claims, false)))
            throw new Exception("Error: failed to verify token!");
        
        // return
        JSONObject account = (JSONObject) claims.get(Config._ACCOUNT_NAME_);
        
        AccountInfo ret = new AccountInfo((String) account.get(Config._ACC_ID_NAME_),
                            (String) account.get(Config._ACC_USERNAME_NAME_),
                            (String) account.get(Config._ACC_DEVICE_ID_),
                            (String) account.get(Config._ACC_DEVICE_TYPE_),
                            (String) account.get(Config._ACC_DEVICE_OS_),
                            (String) account.get(Config._ACC_LOCATION_CODE_)
                            );
        return ret;
    }

    public static String getDataFromJson(JSONObject json, String key1, String key2) {
        return (String) ((JSONObject) json.get(key1)).get(key2);

    }
}
