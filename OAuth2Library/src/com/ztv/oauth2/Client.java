package com.ztv.oauth2;

import com.ztv.oauth2.manager.Base64Coder;
import com.ztv.oauth2.manager.OAuth2Manager;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Client {
    public static void main(String[] args) throws Exception {
        /*
        List<String> scopes = new ArrayList<String>();
        scopes.add(ScopeList.ACCOUNT);
        scopes.add(ScopeList.CLIPS);
        scopes.add(ScopeList.KARAOKE);
        scopes.add(ScopeList.LIVE);
        scopes.add(ScopeList.MUSIC);
        scopes.add(ScopeList.LAUNCHER);
        scopes.add(ScopeList.VOD);
*/
//        String token = OAuth2Generator.genToken(new AccountInfo("-1", "t_account", "t_device", "stb", "android", "HN"), true);
        String token = "eyJycyI6IjIwMzA3NWYiLCJjIjp7ImFjIjp7ImR0Ijoic3RiIiwiZHYiOiIwODIyMTUxMTAwMDAwMyIsIm9zIjoiYW5kcm9pZCIsImxjIjpudWxsLCJpZCI6IjYyNCIsInVzIjoiMDgyMjE1MTEwMDAwMDNfMTQ1MDgzNzc5NTE3MCJ9LCJ0byI6IjE0NTI2NzM3Nzc3MTEiLCJydG8iOiIxNDUyNjc2NDc3NzExIn0sInMiOiI4YmVkNjY2In0=";
        System.out.println(token);
        //            System.out.println(token.get("token"));
        //            System.out.println(token.get("refresh_token"));

        System.out.println(Base64Coder.decodeString(token));
        Date date = new Date(1452676477711L);
        System.out.println(date.toString());
        /*
        
        long count = 0;
        for (long i = 0; i < 10000000000l; i++) {
            count ++;
        }
        
        RefreshItem result = OAuth2Generator.refreshToken(token);
        System.out.println(Base64Coder.decodeString(result.getToken()));
        */
        
//        String deviceId = "crnvtib";
//        String deviceType =  deviceId != null && !deviceId.toUpperCase().startsWith("V-") ? "STB" : "MOBILE";
//        System.out.println(deviceType);
        
        JSONObject tokenObj = (JSONObject) JSONValue.parse(Base64Coder.decodeString(token));
//        AccountInfo info = OAuth2Manager.verifyToken(tokenObj);
//        AccountInfo info = OAuth2Manager.verifyRefreshToken(tokenObj);
//        System.out.println(info.getId());
//        System.out.println(info.getAccountId());
//        System.out.println(info.getDeviceId());
        
        RefreshItem rItem = OAuth2Generator.refreshToken(token);
        System.out.println(Base64Coder.decodeString(rItem.getToken()));
        //        for (int i = 0; i < info.getScopes().size(); i++) {
        //            System.out.println(info.getScopes().get(i));
        //        }
        //
        //        System.out.println(info.getScopes().contains(ScopeList.LIVE));
        //        System.out.println(info.getScopes().contains(ScopeList.VOD));
    }
}
