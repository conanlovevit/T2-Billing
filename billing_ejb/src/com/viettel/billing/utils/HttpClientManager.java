package com.viettel.billing.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;

import java.io.InputStreamReader;

import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.URL;

import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.message.BasicNameValuePair;

import org.apache.http.util.EntityUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class HttpClientManager {
    private static String genQuery(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        Iterator iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            String value = (String) (mapEntry.getValue() != null ? mapEntry.getValue() : "");
            sb.append((String) mapEntry.getKey() + "=" +
                      URLEncoder.encode(value, java.nio.charset.StandardCharsets.UTF_8.name()));
            sb.append("&");
        }
        return sb.toString();
    }

    public static JSONObject sendHttpPostRequest_json(String url, Map<String, String> lParam) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            // Request parameters and other properties.
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            if (lParam.size() > 0) {
                for (String key : lParam.keySet()) {
                    params.add(new BasicNameValuePair(key, lParam.get(key)));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            if (respEntity != null) {
                // EntityUtils to get the response content
                String content = EntityUtils.toString(respEntity);
//                    System.out.println("------ sendHttpPostRequest_json start ------");
//                    System.out.println("sendHttpPostRequest_json: " + content);
//                    System.out.println("------ sendHttpPostRequest_json end ------");

                JSONParser parser = new JSONParser();
                return (JSONObject) parser.parse(content);
            }
        } catch (Exception e) {
            // writing exception to log
//            e.printStackTrace();
        }
        return null;
    }


    public static String sendPostHttp(String url, Map<String, String> params) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        String urlParameters = genQuery(params);

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", String.valueOf(urlParameters.getBytes("UTF-8").length));

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
