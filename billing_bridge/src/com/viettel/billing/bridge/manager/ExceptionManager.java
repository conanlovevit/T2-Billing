package com.viettel.billing.bridge.manager;

import com.viettel.billing.publicItem.consts.ExceptionDefines;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.json.simple.JSONObject;


public class ExceptionManager {
    private static class ErrorItem {
        public int code;
        public String message;
        
        public ErrorItem(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    private static ExceptionManager.ErrorItem depositeException(String ex) {
        if (ex.startsWith(ExceptionDefines.CARD_FAILED))
            return new ErrorItem(9809, ex.substring(ExceptionDefines.CARD_FAILED.length()));
        return null;
    }

    private static ExceptionManager.ErrorItem buyPackageException(String ex) {
        if (ex.startsWith(ExceptionDefines.PGT_NOT_ENOUGH_MONEY))
            return new ErrorItem(902, "Tài khoản không đủ tiền thực hiện giao dịch!");
        return null;
    }

    public static JSONObject processException(String action, Exception ex) {
        if (ex.getMessage() == null) return genDefaultErrorException(ex);
        
        if (ex.getMessage().equalsIgnoreCase("_999999")) {
            return JsonFormat.genErrorResult(901, "Bạn cần đăng nhập để thực hiện chức năng này!", null);
        }
        
        ErrorItem ret = null;
        if (BridgeActionDefines._DEPOSITE_.equalsIgnoreCase(action)) {
            ret = depositeException(ex.getMessage());
        }
        
        if (BridgeActionDefines._BUY_PACKAGE_.equalsIgnoreCase(action)) {
            ret = buyPackageException(ex.getMessage());
        }
        
        if (ret == null) {
            return genDefaultErrorException(ex);
        } else {
            return JsonFormat.genErrorResult(ret.code, ret.message, null);
        }
    }

    private static JSONObject genDefaultErrorException(Exception ex) {
        System.out.println("Billing bridge Error: " + ex.getMessage());
        ex.printStackTrace();
        // get exception
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        
        return JsonFormat.genErrorResult(-1, ex.getMessage(), sw.toString());
//        return JsonFormat.genErrorResult(-1, "Hệ thống đang nâng cấp, quý khách vui lòng thử lại sau ít phút!", sw.toString());
    }    
    
    public static JSONObject genUnloginException() {
        return JsonFormat.genErrorResult(901, "Bạn cần đăng nhập để thực hiện chức năng này!", null);
        
    }
}
