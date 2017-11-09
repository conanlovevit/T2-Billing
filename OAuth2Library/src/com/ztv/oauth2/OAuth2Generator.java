package com.ztv.oauth2;

import com.ztv.oauth2.manager.Base64Coder;

import com.ztv.oauth2.manager.Config;
import com.ztv.oauth2.manager.OAuth2Manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class OAuth2Generator {
    private static String genTokens(JSONObject token, boolean isRefresh) throws Exception {
        JSONObject claims = (JSONObject) token.get(Config._CLAIMS_NAME_);
        // gen token
        
        token.remove(Config._SIGNATURE_NAME_);
        token.put(Config._SIGNATURE_NAME_, OAuth2Manager.genSignature(claims, false));
        if (isRefresh) {
            token.remove(Config._REFRESH_SIGNATURE_NAME_);
            token.put(Config._REFRESH_SIGNATURE_NAME_, OAuth2Manager.genSignature(claims, true));
        }
        
//        System.out.println("genToken: " + Base64Coder.encodeString(token.toString()));
        return Base64Coder.encodeString(token.toString());
    }
    
    public static String genToken(AccountInfo accountInfo, boolean isRefresh) throws Exception {
        // prepare token
        JSONObject token = new JSONObject();
//        token.put(Config._HEADER_NAME_, OAuth2Manager.genHeader());
        
        JSONObject claims = OAuth2Manager.genClaims(accountInfo, null);
//        System.out.println("claims11 : " + claims.toString());
//        String claimsStr = claims.toString();

        token.put(Config._CLAIMS_NAME_, claims);
        
        return genTokens(token, isRefresh);
    }
    
    public static RefreshItem refreshToken(String oldToken) {
        try {
            RefreshItem ret = new RefreshItem();
            // decode token
            JSONObject token = (JSONObject) JSONValue.parse(Base64Coder.decodeString(oldToken));
            
            // verify token for refresh
            AccountInfo account = OAuth2Manager.verifyRefreshToken(token);
            if (account == null) return null;

            JSONObject newToken = new JSONObject();
            JSONObject claims = OAuth2Manager.genRefreshClaims(account, null, ((JSONObject) token.get(Config._CLAIMS_NAME_)).get(Config._REFRESH_TIMEOUT_NAME_).toString());
            newToken.put(Config._CLAIMS_NAME_, claims);
            
            
            // update token
//            JSONObject claims = (JSONObject) token.get(Config._CLAIMS_NAME_);
//            ((JSONObject) token.get(Config._CLAIMS_NAME_)).remove(Config._TIMEOUT_NAME_);
//            ((JSONObject) token.get(Config._CLAIMS_NAME_)).put(Config._TIMEOUT_NAME_, String.valueOf(System.currentTimeMillis() + Config._TIMEOUT_MINISECOND_));
//            claims.remove(Config._REFRESH_TIMEOUT_NAME_);
//            claims.put(Config._REFRESH_TIMEOUT_NAME_, String.valueOf(System.currentTimeMillis() + Config._REFRESH_TIMEOUT_MINISECOND_));
            
            ret.setToken(genTokens(newToken, true));
            ret.setAccount(account);
            return ret;
        } catch (Exception ex) {
//            ex.printStackTrace();
            return null;
        }
    }
}
