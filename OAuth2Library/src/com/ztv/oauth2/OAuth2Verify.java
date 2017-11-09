package com.ztv.oauth2;

import com.ztv.oauth2.manager.Base64Coder;
import com.ztv.oauth2.manager.Config;
import com.ztv.oauth2.manager.OAuth2Manager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class OAuth2Verify {
    private final static String HTTP_ACCOUNT_PARAM  = "__osm_account__";
    private final static String HTTP_OLDTOKEN_PARAM = "__osm_old_token__";
    private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";
    
    private static boolean errorEvent(boolean isAutoResponseError, HttpServletResponse response, String apiVersion, String message) throws IOException {
        if (isAutoResponseError) {
            JSONObject sRespone = new JSONObject();
            sRespone.put("apiVersion", apiVersion);
            sRespone.put("status", -1);
            sRespone.put("data", null);
            
            JSONObject error = new JSONObject();
            error.put("code", 401);
            error.put("message", message);
            
            sRespone.put("error", error);
            
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            out.print(sRespone.toString());
            out.close();
        }
        
        return false;
    }
    
    private static boolean errorEvent(String token, boolean isAutoResponseError, HttpServletResponse response, String apiVersion, String message) throws IOException {
        if (isAutoResponseError) {
            JSONObject sRespone = new JSONObject();
            sRespone.put("apiVersion", apiVersion);
            sRespone.put("status", -1);
            sRespone.put("data", null);
            
            JSONObject error = new JSONObject();
            error.put("code", 401);
            error.put("message", message);
            error.put("token", token);
            
            sRespone.put("error", error);
            
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            out.print(sRespone.toString());
            out.close();
        }
        
        return false;
    }
    
    public static boolean verify(HttpServletRequest request, HttpServletResponse response, String scope, boolean isAutoResponseError, String apiVersion) throws IOException {
//        System.out.println("verify: " + request.getHeader("Authorization"));
        // check token header exist
        if (request.getHeader("Authorization") == null) return errorEvent(isAutoResponseError, response, apiVersion, "Error: no Authorization in header!");
        String token = request.getHeader("Authorization");
        if (!token.startsWith(Config._TOKEN_HEADER_START)) return errorEvent(isAutoResponseError, response, apiVersion, "Error: not bear Authorization!");
        token = token.substring(Config._TOKEN_HEADER_START.length()).trim();
//        System.out.println("verify: " + token);
        
        // if new token equal old token
        if (token.equals(request.getSession().getAttribute(HTTP_OLDTOKEN_PARAM))) return true;
        
        // refesh
        request.getSession().removeAttribute(HTTP_OLDTOKEN_PARAM);
        request.getSession().removeAttribute(HTTP_ACCOUNT_PARAM);
        
        // verify
        try {
            JSONObject tokenObj = (JSONObject) JSONValue.parse(Base64Coder.decodeString(token));
            AccountInfo account = OAuth2Manager.verifyToken(tokenObj);

            // check scope
//            if (account.getScopes().contains(scope) == false)
//                return errorEvent(isAutoResponseError, response, apiVersion, "Error: not accept for scope '" + scope + "'");
            
            request.getSession().setAttribute(HTTP_ACCOUNT_PARAM, account);
            request.getSession().setAttribute(HTTP_OLDTOKEN_PARAM, token);

            return true;
        } catch (Exception ex) {
            System.out.println("Oauth2 - verify: " + ex.getMessage());
//            ex.printStackTrace();
            return errorEvent(token, isAutoResponseError, response, apiVersion, ex.getMessage());
        }
    }
    
    public static boolean isLogin(HttpServletRequest request) {
        if (request.getSession().getAttribute(HTTP_ACCOUNT_PARAM) == null) return false;
        AccountInfo account = (AccountInfo) request.getSession().getAttribute(HTTP_ACCOUNT_PARAM);
        return (account.getId().length() > 0);
    }
    
    public static AccountInfo getAccount(HttpServletRequest request) {
        if (request.getSession().getAttribute(HTTP_ACCOUNT_PARAM) == null) return null;
        AccountInfo account = (AccountInfo) request.getSession().getAttribute(HTTP_ACCOUNT_PARAM);
        if (account.getId().length() <= 0) return null;
        else return account;
    }
    
    public static boolean checkScope(HttpServletRequest request, String scope) {
//        if (request.getSession().getAttribute(HTTP_ACCOUNT_PARAM) == null) return false;
//        AccountInfo account = (AccountInfo) request.getSession().getAttribute(HTTP_ACCOUNT_PARAM);
//        return (account.getScopes().contains(scope));
        return true;
    }
}
