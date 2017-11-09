package com.content_log.ejb.bl.core;

import com.content_log.ejb.api.GcmManager;
import com.content_log.ejb.bl.model.TblGcm;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

import com.vtce.vod.remote.bean.entity.Vod;

import java.io.IOException;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

public class GcmSendingThread implements Runnable {
    private static final String _SERVER_KEY_ = "AIzaSyAnqkHrn4k0URBrxGE63LUJjeTALqmTvsk";
    private static final int _TTL_ = 10 * 60; //10 minutes
    private static final String collapseKey = "VOD_RECOMMEND";

    @Override
    public void run() {
        System.out.println("udpBridgeBean ---> postGcm: START");

        List<TblGcm> lst = getGcmList();
        if (lst == null) {
            System.out.println("udpBridgeBean ---> postGcm: STOP by duplication");
            return;
        }
        if (lst.size() == 0) {
            System.out.println("udpBridgeBean ---> postGcm: STOP by no gcm client");
            return;
        }

        Vod vod = VodNotifyManager.getVod();
        if (vod == null) {
            System.out.println("udpBridgeBean ---> postGcm: STOP by get vod");
            return;
        }

        List<TblGcm> olst = new ArrayList<TblGcm>();
        List<TblGcm> nlst = new ArrayList<TblGcm>();
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).getAppCode() >= 50) nlst.add(lst.get(i));
            else olst.add(lst.get(i));                                               
        }

        Sender sender = new Sender(_SERVER_KEY_);
        
        System.out.println("udpBridgeBean ---> send old: " + olst.size());
        Message message = getMessage(vod);
        sendMessage(sender, message, olst);

        System.out.println("udpBridgeBean ---> send new: " + nlst.size());
        message = getHtmlMessage2(vod);
        sendMessage(sender, message, nlst);
        
        System.out.println("udpBridgeBean ---> postGcm: FINISH");
    }
    
    private void sendMessage(Sender sender, Message message , List<TblGcm> lst) {
        int index = 0;
        while (index < lst.size()) {
            // create reg list
            List<String> regIds = new ArrayList<String>();
            for (int i = 0; i < 100; i++) {
                regIds.add(lst.get(index).getGcmId());
                if (++index >= lst.size()) break;
            }
            // send message
            try {
                if (regIds.size() > 0) sender.send(message, regIds, 0);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }
    
    private Message getMessage(Vod vod) {
        Message message = new Message.Builder()
//            .collapseKey(collapseKey)
            .timeToLive(_TTL_)
            .addData("type", "vod")
            .addData("id", vod.getId().toString())
            .addData("name", vod.getName())
            .addData("image", vod.getPosterhorizon())
            .addData("desc", vod.getDescription())
            .addData("rating", vod.getRating().toString())
            .build();
//        System.out.println(message.toString());
        return message;
    }
    
    private Message getHtmlMessage(Vod vod) {
        Message message = new Message.Builder()
//            .collapseKey(collapseKey)
            .timeToLive(_TTL_)
            .addData("package", "com.ztv.notifications.AdsManagerActivity")
            .addData("display_time", "30")
            .addData("width", "500")
            .addData("height", "200")
            .addData("key_code", "5")
            .addData("data", "{\"type\":\"movie\",\"id\":" + vod.getId().toString() + "}")
            .addData("html", "\n" + 
            "<html>\n" + 
            "<head>\n" + 
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" + 
            "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" + 
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
            "	<title>Layout Template ZTV</title>		\n" + 
            "	<style>\n" + 
            "html,body,div,span,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,b,abbr,acronym,address,big,cite,code,del,dfn,em,font,img,dl,dt,dd,ol,ul,li,fieldset,form,label{background:transparent;border:0;font-size:100%;margin:0;outline:0;padding:0;}\n" + 
            "body{font-family:Arial,Helvetica,sans-serif; font-size:14px; background: transparent;}\n" + 
            "ul {list-style: none outside none; margin:0; padding:0;}\n" + 
            "ul li {display: inline-block;}\n" + 
            "\n" + 
            ".popup{ background:rgba(255, 0,0, 0.2); width: 618px; height: 138px;}\n" + 
            ".popup_img{float: left; width: 188px;}\n" + 
            ".popup_content{float: left;width:430px;}\n" + 
            ".popup_img img{padding:15px 0 0 15px;  float: left;}\n" + 
            ".popup_content h2, .popup p{color: #000; }\n" + 
            ".popup_content p{margin-left: 20px;}\n" + 
            ".popup_content h2{font-size: 24px; text-align: center;padding: 15px;}\n" + 
            "img, video {height: auto;max-width: 100%;}\n" + 
            "\n" + 
            "@media only screen and (width:980px){\n" + 
            "	.container{width:980px; padding: 0;}\n" + 
            "}\n" + 
            "@media only screen and (max-width:800px){\n" + 
            ".slideshow{display:none;}\n" + 
            ".nav.navbar-nav a {color: #fff;font-size: 11px;font-weight: bold;}\n" + 
            ".navbar-inverse .navbar-nav > li > a {color: #fff;padding:10px 8px;line-height: 30px;display: inline-block;font-weight:600;width: 100%;text-align:center;}\n" + 
            ".main_inner h2{font-size:12px;}\n" + 
            "}\n" + 
            "@media only screen and (max-width:360px){\n" + 
            "table th {background-color: #eeeeee;background-image: -moz-linear-gradient(center top , #f6f6f6, #eeeeee);border: 1px solid #cccccc;color: #000000;font-size: 12px;font-weight: bold;padding: 10px 0px 8px;text-align: center;text-shadow: 0 1px 1px #ffffff;}\n" + 
            "table td {border: 1px solid #cccccc;padding: 7px 0px;text-align: center;}\n" + 
            "#header strong, #header h1{font-size:14px;}\n" + 
            "}\n" + 
            ".adv{width:100%;float:left;height:auto;margin:20px 0;}\n" + 
            "	</style>\n" + 
            "	<link rel=\"stylesheet\" href=\"view/css/style.css\" type=\"text/css\" />\n" + 
            "</head>\n" + 
            "<body>\n" + 
            "	<div class=\"popup\">\n" + 
            "		<div class=\"popup_img\">		\n" + 
            "			<img src=\"" + vod.getPosterhorizon() + "\" alt=\"abc\"/>\n" + 
            "		</div>\n" + 
            "		<div class=\"popup_content\">		\n" + 
            "			<h2>" + vod.getName() + "</h2>\n" + 
            "			<p>" + vod.getDescription() + "</p>\n" + 
            "		</div>		\n" + 
            "	</div>\n" + 
            "</body>\n" + 
            "</html>")
            .build();
//        System.out.println(message.toString());
        return message;
    }
    
    private static String HTML_TEMPLATE_old = "<html>\n" + 
    "<head>\n" + 
    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" + 
    "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" + 
    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
                                          
    "	<script src='http://m.kenhitv.vn/ztvt2/rating/jquery.js' type=\"text/javascript\"></script>\n" + 
    "	<script src='http://m.kenhitv.vn/ztvt2/rating/jquery.MetaData.js' type=\"text/javascript\" language=\"javascript\"></script>\n" + 
    " 	<script src='http://m.kenhitv.vn/ztvt2/rating/jquery.rating.js' type=\"text/javascript\" language=\"javascript\"></script>\n" + 
    " 	<link href='http://m.kenhitv.vn/ztvt2/rating/jquery.rating.css' type=\"text/css\" rel=\"stylesheet\"/> \n" + 
    "	<link href='http://m.kenhitv.vn/ztvt2/rating/style.css' type=\"text/css\" rel=\"stylesheet\"/> \n" + 
    "	<link href='http://m.kenhitv.vn/ztvt2/rating/jquery-ui.min.js' type=\"text/javascript\" language=\"javascript\"></script>\n" + 
    "</head>\n" + 
    "<body>\n" + 
    "	<div class=\"popup\">\n" + 
    "		<div class=\"popup_img\">\n" + 
    "			<img src=\"%image%\"/>\n" + 
    "		</div>\n" + 
    "		<div class=\"popup_content\">\n" + 
    "			<h2>%name%</h2>\n" + 
    "			<p>%description%</p>\n" + 
    "		  	<div class=\"Clear\" id=\"documentation\">\n" + 
    "			     <input type=\"radio\" class=\"star {split:2}\" name=\"api-readonly-test\" value=\"1\" %1%/>\n" + 
    "			     <input type=\"radio\" class=\"star {split:2}\" name=\"api-readonly-test\" value=\"2\" %2%/>\n" + 
    "			     <input type=\"radio\" class=\"star {split:2}\" name=\"api-readonly-test\" value=\"3\" %3%/>\n" + 
    "			     <input type=\"radio\" class=\"star {split:2}\" name=\"api-readonly-test\" value=\"4\" %4%/>\n" + 
    "			     <input type=\"radio\" class=\"star {split:2}\" name=\"api-readonly-test\" value=\"5\" %5%/>\n" + 
    "			     <input type=\"radio\" class=\"star {split:2}\" name=\"api-readonly-test\" value=\"6\" %6%/>\n" + 
    "			     <input type=\"radio\" class=\"star {split:2}\" name=\"api-readonly-test\" value=\"7\" %7%/>\n" + 
    "			     <input type=\"radio\" class=\"star {split:2}\" name=\"api-readonly-test\" value=\"8\" %8%/>\n" + 
    "			     <input type=\"radio\" class=\"star {split:2}\" name=\"api-readonly-test\" value=\"9\" %9%/>\n" + 
    "			     <input type=\"radio\" class=\"star {split:2}\" name=\"api-readonly-test\" value=\"10\" %10%/>\n" + 
    "			     \n" + 
    "			     <input type=\"image\" src=\"http://m.kenhitv.vn/ztvt2/view/images/click_enter.png\" border=\"0\"/>\n" + 
    "		  	</div>\n" + 
    "		</div>\n" + 
    "	</div>\n" + 
    "</body>\n" + 
    "</html>";
    
    private String HTML_TEMPLATE = "<html>\n" + 
    "<head>\n" + 
    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" + 
    "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" + 
    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
    "	<style type=\"text/css\">\n" + 
    "		html,body,div,span,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,b,abbr,acronym,address,big,cite,code,del,dfn,em,font,img,dl,dt,dd,ol,ul,li,fieldset,form,label{background:transparent;border:0;font-size:100%;margin:0;outline:0;padding:0;}\n" + 
    "		body{font-family:Arial,Helvetica,sans-serif; font-size:14px; background: transparent;}\n" + 
    "		.popup{ background:rgba(0, 0, 0, 0.2); width: 618px; height: 138px;}\n" + 
    "		.popup_img{float: left; width: 188px;}\n" + 
    "		.popup_content{float: left;width:410px;padding: 10px;}\n" + 
    "		.popup_img img{padding:10px 0 0 15px;  float: left; width: 172px; height: 116px;}\n" + 
    "		.popup_content h2, .popup p{color: #fff; margin: 0 20px;}\n" + 
    "		.popup_content p{line-height: 20px;}\n" + 
    "		.popup_content h2{font-size: 24px; text-align: center;padding: 0 0 5px 0;}\n" + 
    "		.bg_start{background: url(http://m.kenhitv.vn/ztvt2/view/images/start_white.png) repeat-x; width: 126px; height: 25px; float: left;margin-top:10px; }\n" + 
    "		.bg_start1{background: url(http://m.kenhitv.vn/ztvt2/view/images/start-yellor.png) repeat-x; width: %rate%%; height: 25px; float: left; }\n" + 
    "		.Clear{display: block;margin: 0 auto; padding: 5px 20px;}\n" + 
    "		.Clear input[type=image] {padding-left: 80px;}\n" + 
    "		.star-rating-control{padding: 10px 0;float: left;}\n" + 
    "	</style>	\n" + 
    "</head>\n" + 
    "<body>\n" + 
    "	<div class=\"popup\">\n" + 
    "		<div class=\"popup_img\">		\n" + 
    "			<img src=\"%image%\" alt=\"abc\"/>\n" + 
    "		</div>\n" + 
    "		<div class=\"popup_content\">		\n" + 
    "			<h2><marquee scrollamount=\"3\">%name%</marquee></h2>\n" + 
    "			<p id=\"chuoi\">%description%</p>		\n" + 
    "		  	<div class=\"Clear\">			   \n" + 
    "			  	<div class=\"bg_start\">\n" + 
    "			  		<div class=\"bg_start1\"></div>\n" + 
    "			  	</div>\n" + 
    "			     <input type=\"image\" name=\"submit\" src=\"http://m.kenhitv.vn/ztvt2/view/images/click_enter.png\" border=\"0\" alt=\"Submit\" \"/>	     \n" + 
    "		  	</div>		\n" + 
    "		</div>		\n" + 
    "	</div>\n" + 
    "</body>\n" + 
    "</html>";
    
    private String standardString(String str, int len) {
        if (str == null || str.length() <= len + 2) return str;
        return str.substring(0, len) + " ...";
    }
    
    private Message getHtmlMessage2(Vod vod) {
        String html = HTML_TEMPLATE;
        html = html.replace("%image%", vod.getPosterhorizon());
        html = html.replace("%name%", vod.getName());
        html = html.replace("%description%", standardString(vod.getDescription(), 100));
//        long rate = Math.round(vod.getRating() * 20);
        long rate = 70 + vod.getId() % 30;
        html = html.replace("%rate%", String.valueOf(rate));
        Message message = new Message.Builder()
    //            .collapseKey(collapseKey)
            .timeToLive(_TTL_)
            .addData("package", "com.ztv.notifications.adsmanager.START_SERVICE")
            .addData("display_time", "30")
            .addData("width", "618")
            .addData("height", "138")
            .addData("key_code", "0")
            .addData("data", "{\"type\":\"movie\",\"id\":" + vod.getId().toString() + "}")
            .addData("html", html)
            .build();
    //        System.out.println(message.toString());
        return message;
    }
    
    private static GcmManager ejb = null;
    private List<TblGcm> getGcmList() {
        try {
            if (ejb == null) ejb = getEjb();
            Timestamp yesterday = new Timestamp(System.currentTimeMillis() - 86400000);
            return ejb.getTblGcmFindActive(yesterday);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<TblGcm>();
        }
    }

    private GcmManager getEjb() throws Exception {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);            
        //        final Context context = new InitialContext();

        return (GcmManager) context.lookup("ejb:/log_content_ejb//GcmManager!com.content_log.ejb.api.GcmManager");
    }
    
}
