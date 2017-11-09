package com.content_log.ejb.client;

import com.google.android.gcm.server.Message;

import com.google.android.gcm.server.Sender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.JSONParser;

public class SenderTest {
    private final static String authKey = "AIzaSyAnqkHrn4k0URBrxGE63LUJjeTALqmTvsk";
    private final static String regId = "APA91bFbebaY3Ku63mDekj41371__CK6CEOJO6V2P-4MxaSHHZ4aV2EyuljrrvC66uFTQ5ly_VvSaGjNiHOYNJheDFdI90uvAcojSxZJu5wlqyCm5DtBWxPKC1hEhvWfIUU_pj4cPI4x";

    private final static String collapseKey = "collapseKey";
    private final static boolean delayWhileIdle = true;
    private final static boolean dryRun = true;
    private final static String restrictedPackageName = "package.name";
    private final static int retries = 42;
    private final static int ttl = 30*60;
    private final static JSONParser jsonParser = new JSONParser();
    
    private final static Message message =
        new Message.Builder()
//            .collapseKey(collapseKey)
//            .delayWhileIdle(delayWhileIdle)
//            .dryRun(dryRun)
//            .restrictedPackageName(restrictedPackageName)
            .timeToLive(ttl)
    .addData("type", "vod")
    .addData("id", "1")
    .addData("name", "Film Title")
    .addData("image", "horizontal_poster.jpg")
    .addData("desc", "Film Description")
    .addData("rating", "4.3")
            .build();

    private static Message getHtmlMessage(String title) {
        Message message = new Message.Builder()
            .collapseKey(collapseKey)
            .delayWhileIdle(delayWhileIdle)
            .dryRun(dryRun)
            .timeToLive(ttl)
            .addData("package", "com.ztv.notifications.AdsManagerActivity")
            .addData("display_time", "30")
            .addData("width", "500")
            .addData("height", "200")
            .addData("key_code", "5")
            .addData("data", "{\"type\":\"movie\",\"id\":1}")
            .addData("html", "\n" + 
            "<html>\n" + 
            "<head>\n" + 
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" + 
            "   <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" + 
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
            "   <title>Layout Template ZTV</title>              \n" + 
            "   <style>\n" + 
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
            "   .container{width:980px; padding: 0;}\n" + 
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
            "   </style>\n" + 
            "   <link rel=\"stylesheet\" href=\"view/css/style.css\" type=\"text/css\" />\n" + 
            "</head>\n" + 
            "<body>\n" + 
            "   <div class=\"popup\">\n" + 
            "           <div class=\"popup_img\">               \n" + 
            "                   <img src=\"http://m.kenhitv.vn/playlist/layout/view/images/img.jpg\" alt=\"abc\"/>\n" + 
            "           </div>\n" + 
            "           <div class=\"popup_content\">           \n" + 
            "                   <h2>" + title + "</h2>\n" + 
            "                   <p>template layout template layout template layout template layout template layout template Layout template layout template layout</p>\n" + 
            "           </div>          \n" + 
            "   </div>\n" + 
            "</body>\n" + 
            "</html>")
            .build();
        System.out.println(message.toString());
        return message;
    }


    
    public static void main(String[] args) throws IOException {
        System.out.println("In Time là một bộ phim hành động của đạo diễn Shane Black, nối tiếp phần 2 trước. Nhân vật chính".length());
        
        double t = 2.2;
        System.out.println(Math.round(t*2));
        
//        Message message_ = getHtmlMessage(args[0]);
//
//        Sender sender = new Sender(authKey);
//        List<String> regIds = new ArrayList<String>();
//        regIds.add(args[1]);
//        sender.send(message_, regIds, retries);
    }
}
