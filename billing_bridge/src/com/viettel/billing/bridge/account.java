package com.viettel.billing.bridge;

import com.viettel.billing.bridge.manager.BridgeActionDefines;
import com.viettel.billing.bridge.manager.ExceptionManager;
import com.viettel.billing.bridge.manager.HttpUtils_;
import com.viettel.billing.bridge.manager.JsonFormat;
import com.viettel.billing.bridge.manager.BillingManager;

import com.viettel.billing.bridge.manager.BridgeConfig;
import com.viettel.billing.bridge.manager.GcmManager;
import com.viettel.billing.bridge.manager.LogManager;

import com.ztv.oauth2.OAuth2Verify;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.simple.JSONObject;

@WebServlet(name = "account", urlPatterns = { "/account" })
public class account extends HttpServlet {
    private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // debug
        long __time__ = HttpUtils_.logServletParam_start(request, "account");
        
        response.setContentType(CONTENT_TYPE);

        if (!OAuth2Verify.verify(request, response, null, true, BridgeConfig.version)) return;

        JSONObject data = null;
        String action = HttpUtils_.getServletParam(request, "action");
        try {
            boolean isFound = false;
            if (BridgeActionDefines._GET_PACKAGE_.equalsIgnoreCase(action)) {
                data = BillingManager.getPackages(request);
                isFound = true;
            }
            if (BridgeActionDefines._BUY_PACKAGE_.equalsIgnoreCase(action)) {
                data = BillingManager.buyPackages(request);
                isFound = true;
            }
            if (BridgeActionDefines._REMOVE_PACKAGE_.equalsIgnoreCase(action)) {
                data = BillingManager.removePackage(request);
                isFound = true;
            }
            if (BridgeActionDefines._BALANCE_.equalsIgnoreCase(action)) {
                data = BillingManager.getBalance(request);
                isFound = true;
            }
            if (BridgeActionDefines._HISTORY_.equalsIgnoreCase(action)) {
                data = LogManager.getHistory(request);
                isFound = true;
            }
            if (BridgeActionDefines._DEPOSITE_.equalsIgnoreCase(action)) {
                data = BillingManager.deposite(request);
                isFound = true;
            }
            if (BridgeActionDefines._GCM_REG_.equalsIgnoreCase(action)) {
                data = GcmManager.getContent(request);
                isFound = true;
            }
            if (BridgeActionDefines._GET_ACC_INFOR.equalsIgnoreCase(action)) {
                data = BillingManager.getAccInfor(request);
                isFound = true;
            }
            if (BridgeActionDefines._SET_ACC_INFOR.equalsIgnoreCase(action)) {
                data = BillingManager.setAccInfor(request);
                isFound = true;
            }
            if (!isFound) throw new Exception("No action found or action is not correct!");
        } catch (Exception ex) {
            PrintWriter out = response.getWriter();
            out.print(ExceptionManager.processException(action, ex).toString());
            out.close();
            
            // debug
            HttpUtils_.logServletParam_finish(request, "account", action, __time__, ex);
            return;
        }
        
        // respond
        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        out.print(JsonFormat.genSuccessResult(data).toString());
        out.close();
        
        // debug
        HttpUtils_.logServletParam_finish(request, "account", action, __time__, null);
    }
}
