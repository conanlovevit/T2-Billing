package com.ztv.oauth2.manager;

public class Config {
    public static String _TOKEN_HEADER_START    = "Bearer ";
    
//    public static long _TIMEOUT_MINISECOND_             = 86400000;  // 1 day
    public static long _TIMEOUT_MINISECOND_             = 300000;  // 5 minute
    public static long _REFRESH_TIMEOUT_MINISECOND_     = _TIMEOUT_MINISECOND_ * 10;
    
    public static String _TYPE_NAME_                = "typ";
    public static String _TYPE_VALUE_               = "JWT";

    public static String _ALGORITHM_NAME_           = "alg";
    public static String _ALGORITHM_VALUE_          = "MD5";
    
    public static String _TIMEOUT_NAME_             = "to";
    public static String _REFRESH_TIMEOUT_NAME_     = "rto";
    public static String _SCOPES_NAME_              = "sc";
    public static String _ACCOUNT_NAME_             = "ac";

    public static String _ACC_ID_NAME_              = "id";
    public static String _ACC_USERNAME_NAME_        = "us";
    public static String _ACC_FULLNAME_NAME_        = "fn";
    public static String _ACC_DEVICE_ID_            = "dv";
    public static String _ACC_DEVICE_TYPE_          = "dt";
    public static String _ACC_DEVICE_OS_            = "os";
    public static String _ACC_LOCATION_CODE_        = "lc"; 

    public static String _HEADER_NAME_              = "h";
    public static String _CLAIMS_NAME_              = "c";
    public static String _SIGNATURE_NAME_           = "s";
    public static String _REFRESH_SIGNATURE_NAME_   = "rs";
}
