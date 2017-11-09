package com.viettel.billing.bridge.manager;

import java.util.Date;

import org.json.simple.JSONObject;

public class JsonFormat {
    public static JSONObject genErrorResult(int code, String message, String error_str) {
        JSONObject sRespone = new JSONObject();
        sRespone.put("apiVersion", BridgeConfig.version);
        sRespone.put("status", -1);
        
        JSONObject error = new JSONObject();
        error.put("code", code);
        error.put("message", message);
        error.put("error", error_str);
        sRespone.put("error", error);
        
//        System.out.println("genErrorResult:" + sRespone.toString());
        
        return sRespone;
    }

    public static JSONObject genSuccessResult(JSONObject data) {
        JSONObject sRespone = new JSONObject();
        sRespone.put("apiVersion", BridgeConfig.version);
        sRespone.put("status", 1);
        
        sRespone.put("data", data);
        sRespone.put("__time__", (new Date()).toString());
//        System.out.println("genSuccessResult:" + sRespone.toString());
        
        return sRespone;
    }
}
