package com.ttd.exception;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.ttd.config.WebAppConfigurer;

import freemarker.template.Template;

//捕获spring mvc层的异常并处理
@ControllerAdvice
public class GlobalExceptionHandler {
	@Autowired JavaMailSender mailSender;  
	@Autowired FreeMarkerConfigurer freeMarkerConfigurer; 
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("key1", req.getRequestURL() + ":" + e);
        
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        
        helper.setFrom("tech@shulaibao.com");
        helper.setTo("songjun@totodi.com");
        helper.setSubject("测试邮件（邮件主题）");
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("mail.ftl");//加载资源文件 
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        helper.setText(html, true); // 指定是否为html 
        
        this.mailSender.send(mimeMessage);  
        
        e.printStackTrace();
    }
}
