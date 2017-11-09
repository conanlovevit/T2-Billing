package com.viettel.billing.bridge.manager;

import com.viettel.billing.api.BridgeApi;

import com.viettel.billing.publicItem.AccountInforItem;
import com.viettel.billing.publicItem.AccountItem;
import com.viettel.billing.publicItem.MobileCardDepositeItem;
import com.viettel.billing.publicItem.PackageItem;

import com.viettel.billing.publicItem.applicationItem;
import com.viettel.billing.publicItem.consts.AccountStatusDefines;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.http.HttpServletRequest;


import com.viettel.billing.utils.SecurityManager;

import com.ztv.oauth2.AccountInfo;
import com.ztv.oauth2.OAuth2Generator;
import com.ztv.oauth2.OAuth2Verify;
import com.ztv.oauth2.RefreshItem;

import com.ztv.oauth2.manager.Config;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class BillingManager {
    private static boolean isVerifyRequest(HttpServletRequest request, String[] lKey) throws Exception {
        String signature = HttpUtils_.getServletParam(request, "sign");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lKey.length; i++) {
            sb.append(HttpUtils_.getServletParam(request, lKey[i]));
        }
        sb.append(BridgeConfig.SERVER_KEY);
        
        String md5 = SecurityManager.md5_string(sb.toString());

//        System.out.println("plain -> '" + sb.toString() + "'");
//        System.out.println("md5 -> '" + md5 + "'");
//        System.out.println("sign -> '" + md5.substring(0, 10) + "'");
        
        return signature.equalsIgnoreCase(md5.substring(0, 10));
    }

    private static JSONObject printPackage(PackageItem item) {
        JSONObject jItem = new JSONObject();
        jItem.put("id", item.getId());
        jItem.put("name", item.getName());
        jItem.put("price", item.getPrice());
        jItem.put("duration", item.getDuration());
        
        jItem.put("used", item.isUsed());
        if (item.isUsed()) {
            jItem.put("pa_id", item.getPackageAccountId());
            jItem.put("auto_rebuy", item.isAutoRebuy());
        }
        
        if (item.getExpired() != null) {
            jItem.put("expiredDate", item.getExpired().getTime());
        }
                
        return jItem;        
    }

    public static JSONObject getPackages(HttpServletRequest request) throws Exception {
        Long accId = Long.parseLong(OAuth2Verify.getAccount(request).getId());
        
        List<PackageItem> lst = getEjb().getPackage(accId);
        JSONArray lret = new JSONArray();
        for (int i = 0; i < lst.size(); i++) {
            lret.add(printPackage(lst.get(i)));
        }
        JSONObject ret = new JSONObject();
        ret.put("list", lret);
//        System.out.println(ret.toString());
        return ret;
    }
    
    public static JSONObject buyPackages(HttpServletRequest request) throws Exception {
        Long accId = Long.parseLong(OAuth2Verify.getAccount(request).getId());
        
        PackageItem ret = getEjb().buyPackage(accId, Long.parseLong(HttpUtils_.getServletParam(request, "package_id")));
        return printPackage(ret);
    }
    
    public static JSONObject removePackage(HttpServletRequest request) throws Exception {
        Long accId = Long.parseLong(OAuth2Verify.getAccount(request).getId());
        
        getEjb().removePackage(accId, Long.parseLong(HttpUtils_.getServletParam(request, "pa_id")));
        return null;
    }
    
    private static JSONObject refreshToken(HttpServletRequest request) {
        // check token header exist
        if (request.getHeader("Authorization") == null) return null;
        
        // get old token
        String token = request.getHeader("Authorization");
        if (!token.startsWith(Config._TOKEN_HEADER_START)) return null;
        token = token.substring(Config._TOKEN_HEADER_START.length()).trim();

        RefreshItem newToken = OAuth2Generator.refreshToken(token);
        if (newToken != null && newToken.getAccount().getAccountId().equalsIgnoreCase(HttpUtils_.getServletParam(request, "device"))) {
            JSONObject ret = new JSONObject();
            ret.put("id", newToken.getAccount().getId());
            ret.put("username", newToken.getAccount().getDeviceId());
            ret.put("token", newToken.getToken());
            ret.put("ip", request.getRemoteAddr());
            ret.put("refresh", true);
            return ret;
        }
        return null;
    }

    public static JSONObject login(HttpServletRequest request) throws Exception {
        // check refresh token
        JSONObject rToken = refreshToken(request);
        if (rToken != null) return rToken;
        
        // check timeout
        long time = Long.parseLong(HttpUtils_.getServletParam(request, "time"));
        if (time < System.currentTimeMillis()) {
            System.out.println("--------------------");
            System.out.println("time: " + time + " -> systemcurrent: " + System.currentTimeMillis());
            throw new Exception("Error: timeout request!");
        }
        
        String[] params = {"device", "time", "mac_sign"};
        if (!isVerifyRequest(request, params)) {
            throw new Exception("Error: un-verified request!");
        }
        
        AccountItem acc = getEjb().login(HttpUtils_.getServletParam(request, "device"), 
                                         HttpUtils_.getServletParam(request, "time"), 
                                         HttpUtils_.getServletParam(request, "mac_sign"));
        
        AccountInfo accInfor = new AccountInfo(acc.getId().toString(), acc.getAccountId(), acc.getDeviceId(), acc.getDeviceType(), acc.getDeviceOs(), acc.getLocation());
        
        JSONObject ret = new JSONObject();
        ret.put("id", acc.getId());
        ret.put("username", acc.getDeviceId());
        ret.put("token", OAuth2Generator.genToken(accInfor, true));
        ret.put("ip", request.getRemoteAddr());
        return ret;
    }
    
    public static BridgeApi getEjb() throws Exception {
        final Context context = getContext();            
        return (BridgeApi) context.lookup("ejb:Billing_Ejb/billing_v2_ejb/BridgeApi!com.viettel.billing.api.BridgeApi");
    }
    
    private static Context getContext() throws NamingException {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        return new InitialContext(jndiProperties);            
    }

    public static JSONObject init(HttpServletRequest request) throws Exception {
        JSONObject ret = new JSONObject();
        ret.put("time", System.currentTimeMillis() + BridgeConfig.REQUEST_TIMEOUT_MINISECOND);
        return ret;
    }

    public static JSONObject checkActive(HttpServletRequest request) throws Exception {
        String deviceCode = HttpUtils_.getServletParam(request, "device");
        
        JSONObject ret = new JSONObject();
        ret.put("active", getEjb().checkActive(deviceCode));
//        ret.put("active", true);
        return ret;
    }

    public static JSONObject active(HttpServletRequest request) throws Exception {
        String deviceCode = HttpUtils_.getServletParam(request, "device");
        String sign = HttpUtils_.getServletParam(request, "sign");        
        getEjb().activeDevice(deviceCode, sign);
        
        return null;
    }

    public static JSONObject createDevice(HttpServletRequest request) throws Exception {
        String deviceCode = HttpUtils_.getServletParam(request, "device");
        String mac1 = HttpUtils_.getServletParam(request, "mac1");
        String mac2 = HttpUtils_.getServletParam(request, "mac2");
        String deviceModelCode = "ztvt2";
        getEjb().createDevice(deviceCode, mac1, mac2, deviceModelCode);
        return null;
    }

    public static JSONObject getBalance(HttpServletRequest request) throws Exception {
        Long accId = Long.parseLong(OAuth2Verify.getAccount(request).getId());
        JSONObject ret = new JSONObject();
        ret.put("balance", getEjb().getBalance(accId));
        return ret;
    }

    public static JSONObject deposite(HttpServletRequest request) throws Exception {
        Long accId = Long.parseLong(OAuth2Verify.getAccount(request).getId());
        
        MobileCardDepositeItem depositeItem = new MobileCardDepositeItem();
        depositeItem.setCardType(HttpUtils_.getServletParam(request, "type"));
        depositeItem.setCode(HttpUtils_.getServletParam(request, "code"));
        depositeItem.setSerial(HttpUtils_.getServletParam(request, "serial"));

        depositeItem = getEjb().deposite(accId, depositeItem);

        JSONObject ret = new JSONObject();
        ret.put("money", depositeItem.getMoney());

        return ret;
    }

    public static JSONObject getAccInfor(HttpServletRequest request) throws Exception {
        Long accId = Long.parseLong(OAuth2Verify.getAccount(request).getId());
        AccountInforItem item = getEjb().getAccountInfor(accId);
        
        JSONObject ret = new JSONObject();
        ret.put("mobile", item.getMobile());
        ret.put("address", item.getAddress());
        ret.put("ip", request.getRemoteAddr());
        return ret;
    }

    public static JSONObject setAccInfor(HttpServletRequest request) throws Exception {
        Long accId = Long.parseLong(OAuth2Verify.getAccount(request).getId());
        String mobile = HttpUtils_.getServletParam(request, "mobile");
        String address = HttpUtils_.getServletParam(request, "address");
        AccountInforItem item = new AccountInforItem();
        item.setAddress(address);
        item.setMobile(mobile);
        getEjb().setAccountInfor(accId, item);
        
        return null;
    }
}
