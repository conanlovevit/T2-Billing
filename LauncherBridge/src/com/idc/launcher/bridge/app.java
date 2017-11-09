package com.idc.launcher.bridge;

import com.idc.launcher.bridge.manager.LauncherManager;

import com.idc.launcher.bridge.manager.Version;

import java.io.IOException;
import java.io.PrintWriter;

import java.io.StringWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet(name = "app", urlPatterns = { "/app" })
public class app extends HttpServlet {
    private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    private JSONObject genJsonError(Exception ex) {
        JSONObject item = new JSONObject();
        item.put("message", ex.getMessage());
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        item.put("trace", sw.toString());
        
        return item;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        JSONObject sRespone = new JSONObject();
        sRespone.put("apiVersion", Version.version);
        try {
            sRespone.put("data", LauncherManager.getAppVersionContent());
            sRespone.put("status", 1);
        } catch (Exception e) {
            sRespone.put("error", genJsonError(e));
            sRespone.put("status", -1);
        }
        out.print(sRespone.toString());
        out.close();
    }
}
