 package com.ttd.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.ttd.utils.ObjectUtil;
import com.ttd.utils.ServletContextUtil;
import com.ttd.utils.StreamUtil;

public class BaseController{
	// log4j2
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
    /**
     * 输出JSON对象
     * @param model
     */
    protected void writeJSON(Object model) {
        write(ObjectUtil.object2json(model), "application/json;charset=UTF-8");
    }
    
    /**
     * 输出文本
     * @param txt
     * @param contextType
     */
    protected void write(String txt, String contextType) {
        try {
            if(Strings.isNullOrEmpty(txt)){return;}
            getResponse().setContentType(contextType);
            StreamUtil.writeData(txt.getBytes("UTF-8"), getResponse().getOutputStream());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    
	protected ModelAndView toJSON(Object model) {
	    writeJSON(model);
        return null;
    }
	
	/**
     * 获取请求对象
     * @return
     */
    protected HttpServletRequest getRequest() {
        return ServletContextUtil.getContext().getRequest();
    }
    
    /**
     * 获取响应对象
     * @return
     */
    protected HttpServletResponse getResponse() {
        return ServletContextUtil.getContext().getResponse();
    }
}
