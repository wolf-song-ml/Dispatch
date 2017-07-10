package com.ttd.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
  
public class HttpClientUtil {  
    private RequestConfig requestConfig = RequestConfig.custom()  
        .setSocketTimeout(15000)  
        .setConnectTimeout(15000)  
        .setConnectionRequestTimeout(15000)  
        .build();  
      
    private static HttpClientUtil instance = null;    
    private HttpClientUtil(){}    
    public static HttpClientUtil getInstance(){    
        if (instance == null) {    
            instance = new HttpClientUtil();    
        }    
        return instance;  
    }    
        
    /**  
     * 发送 post请求  
     * @param httpUrl 地址  
     */    
    public String sendHttpPost(String httpUrl) {    
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost      
        return sendHttpPost(httpPost);    
    }    
        
    /**  
     * 发送 post请求  
     * @param httpUrl 地址  
     * @param params 参数(格式:key1=value1&key2=value2)  
     */    
    public String sendHttpPost(String httpUrl, String params) {    
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost      
        try {    
            //设置参数    
            StringEntity stringEntity = new StringEntity(params, "UTF-8");    
            stringEntity.setContentType("application/x-www-form-urlencoded");    
            httpPost.setEntity(stringEntity);    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return sendHttpPost(httpPost);    
    }    
        
    /**  
     * 发送 post请求  
     * @param httpUrl 地址  
     * @param maps 参数  
     */    
    public String sendHttpPost(String httpUrl, Map<String, String> maps) {    
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost      
        // 创建参数队列      
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();    
        for (String key : maps.keySet()) {    
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));    
        }    
        try {    
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return sendHttpPost(httpPost);    
    }    
        
        
    /**  
     * 发送 post请求（带文件）  
     * @param httpUrl 地址  
     * @param maps 参数  
     * @param fileLists 附件  
     */    
    public String sendHttpPost(String httpUrl, Map<String, String> maps, List<File> fileLists) {    
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost      
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();    
        for (String key : maps.keySet()) {    
            meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));    
        }    
        for(File file : fileLists) {    
            FileBody fileBody = new FileBody(file);    
            meBuilder.addPart("files", fileBody);    
        }    
        HttpEntity reqEntity = meBuilder.build();    
        httpPost.setEntity(reqEntity);    
        return sendHttpPost(httpPost);    
    }    
        
    /**  
     * 发送Post请求  
     * @param httpPost  
     * @return  
     */    
    private String sendHttpPost(HttpPost httpPost) {    
        CloseableHttpClient httpClient = null;    
        CloseableHttpResponse response = null;    
        HttpEntity entity = null;    
        String responseContent = null;    
        try {    
            // 创建默认的httpClient实例.    
            httpClient = HttpClients.createDefault();    
            httpPost.setConfig(requestConfig);    
            // 执行请求    
            response = httpClient.execute(httpPost);    
            entity = response.getEntity();    
            responseContent = EntityUtils.toString(entity, "UTF-8");    
        } catch (Exception e) {    
            e.printStackTrace();    
        } finally {    
            try {    
                // 关闭连接,释放资源    
                if (response != null) {    
                    response.close();    
                }    
                if (httpClient != null) {    
                    httpClient.close();    
                }    
            } catch (IOException e) {    
                e.printStackTrace();    
            }    
        }    
        return responseContent;    
    }    
    
    /**  
     * 发送 get请求  
     * @param httpUrl  
     */    
    public String sendHttpGet(String httpUrl) {    
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求    
        return sendHttpGet(httpGet);    
    }    
        
    /**  
     * 发送 get请求Https  
     * @param httpUrl  
     */    
    public String sendHttpsGet(String httpUrl) {    
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求    
        return sendHttpsGet(httpGet);    
    }    
        
    /**  
     * 发送Get请求  
     * @param httpPost  
     * @return  
     */    
    private String sendHttpGet(HttpGet httpGet) {    
        CloseableHttpClient httpClient = null;    
        CloseableHttpResponse response = null;    
        HttpEntity entity = null;    
        String responseContent = null;    
        try {    
            // 创建默认的httpClient实例.    
            httpClient = HttpClients.createDefault();    
            httpGet.setConfig(requestConfig);    
            // 执行请求    
            response = httpClient.execute(httpGet);    
            entity = response.getEntity();    
            responseContent = EntityUtils.toString(entity, "UTF-8");    
        } catch (Exception e) {    
            e.printStackTrace();    
        } finally {    
            try {    
                // 关闭连接,释放资源    
                if (response != null) {    
                    response.close();    
                }    
                if (httpClient != null) {    
                    httpClient.close();    
                }    
            } catch (IOException e) {    
                e.printStackTrace();    
            }    
        }    
        return responseContent;    
    }    
        
    /**  
     * 发送Get请求Https  
     * @param httpPost  
     * @return  
     */    
    private String sendHttpsGet(HttpGet httpGet) {    
        CloseableHttpClient httpClient = null;    
        CloseableHttpResponse response = null;    
        HttpEntity entity = null;    
        String responseContent = null;    
        try {    
            // 创建默认的httpClient实例.    
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpGet.getURI().toString()));    
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);    
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();    
            httpGet.setConfig(requestConfig);    
            // 执行请求    
            response = httpClient.execute(httpGet);    
            entity = response.getEntity();    
            responseContent = EntityUtils.toString(entity, "UTF-8");    
        } catch (Exception e) {    
            e.printStackTrace();    
        } finally {    
            try {    
                // 关闭连接,释放资源    
                if (response != null) {    
                    response.close();    
                }    
                if (httpClient != null) {    
                    httpClient.close();    
                }    
            } catch (IOException e) {    
                e.printStackTrace();    
            }    
        }    
        return responseContent;    
    }  
}