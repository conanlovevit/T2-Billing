package com.content_log.brigde;

import com.content_log.ejb.api.LogManager;
import com.content_log.ejb.client.JbossUtils;

import java.io.IOException;
import java.io.PrintWriter;

import java.io.StringWriter;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.NamingException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.simple.JSONObject;

@WebServlet(name = "index", urlPatterns = { "/index" })
public class index extends HttpServlet {
    private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";
    private static final String _VERSION_ = "1.0";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    private JSONObject genDefaultErrorException(Exception ex) {
        ex.printStackTrace();
        // get exception
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        
        return genErrorResult(-1, ex.getMessage(), sw.toString());
    }    

    public JSONObject genErrorResult(int code, String message, String error_str) {
        JSONObject sRespone = new JSONObject();
        sRespone.put("apiVersion", _VERSION_);
        sRespone.put("status", -1);
        
        JSONObject error = new JSONObject();
        error.put("code", code);
        error.put("message", message);
        error.put("error", error_str);
        sRespone.put("error", error);
        
        return sRespone;
    }

    public JSONObject genSuccessResult(JSONObject data) {
        JSONObject sRespone = new JSONObject();
        sRespone.put("apiVersion", _VERSION_);
        sRespone.put("status", 1);
        
        sRespone.put("data", data);
    //        System.out.println("genSuccessResult:" + sRespone.toString());
        
        return sRespone;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        
        PrintWriter out = response.getWriter();
        
        try {
            // call update
            getEjb().createGcm(request.getParameter("device_id"), request.getParameter("gcm_id"));
            
            out.print(genSuccessResult(null));
        } catch (Exception ex) {
            out.print(genDefaultErrorException(ex));
        }
        
        out.close();
    }
    
    public static LogManager getEjb() throws Exception {
        final Context context = getContext();            
        return (LogManager) context.lookup("ejb:/log_content_ejb//LogManager!com.content_log.ejb.api.LogManager");
    }
    
    private static Context getContext() throws NamingException {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        return new InitialContext(jndiProperties);            
    }

    
}
