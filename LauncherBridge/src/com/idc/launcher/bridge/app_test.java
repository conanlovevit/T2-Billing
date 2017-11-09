package com.idc.launcher.bridge;

import com.idc.launcher.bridge.manager.LauncherManager;
import com.idc.launcher.bridge.manager.Version;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.simple.JSONObject;

@WebServlet(name = "app_test", urlPatterns = { "/app_test" })
public class app_test extends HttpServlet {
    private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject ret = new JSONObject();
        ret.put("name", "ztvt2");
        ret.put("package", "com.ztv.t2");
        ret.put("version_code", 31);
        ret.put("version", "1.1");
        ret.put("version_name", "Phiên bản sửa lỗi wifi");
        
        ret.put("forced", true);
        
        ret.put("url", "http://123.30.145.218/app/ConnectedTV_code30.apk");
        ret.put("size", "10224759");
        ret.put("hash_md5", "BA25E29DDC191A7078866F684F7DD156");
        
        ret.put("message", "Phiên bản sửa lỗi wifi");
        ret.put("change_log", "Phiên bản sửa lỗi wifi");


        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        JSONObject sRespone = new JSONObject();
        sRespone.put("apiVersion", Version.version);
        sRespone.put("data", ret);
        sRespone.put("status", 1);
        out.print(sRespone.toString());
        out.close();
    }
}
