package com.ttd.domain.base;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import com.ttd.utils.DateUtil;


/**
 * 系统异常错误
 * @author wolf
 */
public class SystemError {
    private String title;
    private String uri;
    private String url;
    private String parameter;
    private String ip;
    private Exception ex;
    private Date errorDate;
    
    public SystemError() {
        
    }
    
    public SystemError(Exception ex){
        this.ex = ex;
    }
    
    public String getTitle() {
        if(this.title != null) {
            return this.title;
        }
        StringBuffer str = new StringBuffer("【异常】");
        str.append("请求【").append(uri).append("】发生错误");
        return str.toString();
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getUri() {
        return uri;
    }
    
    public String getParameter() {
        return parameter;
    }
    
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getIp() {
        return ip;
    }
    
    public String getEx() {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
    
    public void setEx(Exception ex) {
        this.ex = ex;
    }
    
    public String getErrorDate() {
        if(errorDate == null) {
            errorDate = new Date();
        }
        return DateUtil.dateFormat(errorDate.getTime());
    }
    
    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
    }

	@Override
	public String toString() {
		return "SystemError [title=" + title + ", uri="
				+ uri + ", url=" + url + ", parameter=" + parameter + ", ip="
				+ ip + ", ex=" + ex + ", errorDate=" + errorDate + "]";
	}
    
}
