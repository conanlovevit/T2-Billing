package com.viettel.billing.bridge;

import com.viettel.billing.bridge.manager.BridgeActionDefines;
import com.viettel.billing.bridge.manager.ExceptionManager;
import com.viettel.billing.bridge.manager.HttpUtils_;
import com.viettel.billing.bridge.manager.JsonFormat;

import com.viettel.billing.bridge.manager.BillingManager;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet(name = "authen", urlPatterns = { "/authen" })
public class authen extends HttpServlet {
    private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // debug
        long __time__ = HttpUtils_.logServletParam_start(request, "authen");
        
        response.setContentType(CONTENT_TYPE);
        JSONObject data = null;
        
        String action = HttpUtils_.getServletParam(request, "action");        
        try {
            if (BridgeActionDefines._LOGIN_.equalsIgnoreCase(action)) {
                data = BillingManager.login(request);
            }
            if (BridgeActionDefines._INIT_.equalsIgnoreCase(action)) {
                data = BillingManager.init(request);
            }
            if (BridgeActionDefines._CHECK_ACTIVE_.equalsIgnoreCase(action)) {
                data = BillingManager.checkActive(request);
            }
            if (BridgeActionDefines._ACTIVE_.equalsIgnoreCase(action)) {
                data = BillingManager.active(request);
            }
//            if (BridgeActionDefines._CREATE_DEVICE_.equalsIgnoreCase(action)) {
//                data = BillingManager.createDevice(request);
//            }
        } catch (Exception ex) {
            PrintWriter out = response.getWriter();
            out.print(ExceptionManager.processException(action, ex).toString());
            out.close();

            // debug
            HttpUtils_.logServletParam_finish(request, "authen", action, __time__, ex);
            return;
        }
        
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        out.print(JsonFormat.genSuccessResult(data).toString());
        out.close();

        // debug
        HttpUtils_.logServletParam_finish(request, "authen", action, __time__, null);
    }
}
