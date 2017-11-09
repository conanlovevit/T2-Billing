package com.idc.launcher.bridge;

import com.idc.launcher.bridge.manager.LauncherManager;

import com.idc.launcher.bridge.manager.Version;
import com.idc.launcher.bridge.manager.VodManager;

import java.io.IOException;
import java.io.PrintWriter;

import java.io.StringWriter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet(name = "index", urlPatterns = { "/index" })
public class index extends HttpServlet {
    private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    private void genJsonError(JSONArray jsonError, Exception ex) {
        JSONObject item = new JSONObject();
        item.put("message", ex.getMessage());
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        item.put("trace", sw.toString());
        
        jsonError.add(item);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isError = false;
        
        String customers = request.getParameter("customers");
            
        response.setContentType(CONTENT_TYPE);
        JSONObject sRespone = new JSONObject();
        
        sRespone.put("apiVersion", Version.version);
        JSONArray jsonError = new JSONArray();
        
        Map<String, JSONArray> posterMap = new HashMap<String, JSONArray>();
        
        // get VOD data
        try {
            JSONArray jsonVod = VodManager.getContent();
            posterMap.put("vod", jsonVod);
        } catch (Exception ex) {
            genJsonError(jsonError, ex);
//            NagiosClient.sendErrorMessage(NagiosClient.SERVICE_LAUNCHER, "Vod", ex.getMessage());
            isError = true;
        }
        
        // launcher icon
        try {
            sRespone.put("data", LauncherManager.getContent(posterMap));
//            sRespone.put("data", LauncherManager.getContent(posterMap, request, customers));
        } catch (Exception ex) {
            genJsonError(jsonError, ex);
//            NagiosClient.sendErrorMessage(NagiosClient.SERVICE_LAUNCHER, "Launcher", ex.getMessage());
            isError = true;
        }
        
        
        sRespone.put("error", jsonError);
        
        PrintWriter out = response.getWriter();
        out.print(sRespone.toString());
        out.close();
        
//        if (!isError) NagiosClient.sendSuccessMessage(NagiosClient.SERVICE_LAUNCHER, "", "");
    }
}
