package com.ttd.utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class ServletContextUtil {
    private static ThreadLocal<ServletContextUtil> threadLocal = new ThreadLocal<ServletContextUtil>();
    private HttpServletRequest request;//请求对象
    private HttpServletResponse response;//响应对象
    
    //构造方法
    private ServletContextUtil() {
        threadLocal.set(this);//存入ThreadLocal
    }

    public static ServletContextUtil getContext() {
        ServletContextUtil context = threadLocal.get();
        if (null == context) {
            context = new ServletContextUtil();
        }
        return context;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    
    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
    
    public String getParameter(String key) {
    	if (request == null) {
    		return null;
    	}
    	
    	return StringUtils.isNotBlank(request.getParameter(key)) ? request.getParameter(key) : request.getHeader(key);
    }
    
}
