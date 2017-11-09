package com.viettel.billing_log.publicItem;

public class ContenTypeDefines {
    public final static String SERVICE_VOD                  = "vod";
    public final static String SERVICE_VOD_PREMIUM          = "vod_premium";
    public final static String SERVICE_LIVE                 = "live";
    public final static String SERVICE_KARAOKE              = "karaoke";
    public final static String SERVICE_MCLIP                = "mclip";
    public final static String SERVICE_MUSIC                = "music";
    public final static String SERVICE_GAMESTORE            = "gamestore";
    
    public final static String SUB_PACKAGE                  = "sub_package";
    public final static String PACKAGE                      = "package";
    public final static String CONTENT                      = "content";
    public final static String MOBILE_CARD                  = "mobile_card";
    public final static String SMS                          = "sms";
    public final static String ANYPAY                       = "anypay";
    
    public static String[] getAllService() {
        String[] ret = {SERVICE_VOD, SERVICE_VOD_PREMIUM, 
                        SERVICE_LIVE, SERVICE_KARAOKE, SERVICE_MCLIP, SERVICE_MUSIC, 
                        SERVICE_GAMESTORE, PACKAGE, SUB_PACKAGE};
        return ret;
    }

    public static String[] getAllPaymentChannel() {
        String[] ret = {MOBILE_CARD, SMS, ANYPAY};
        return ret;
    }
}
