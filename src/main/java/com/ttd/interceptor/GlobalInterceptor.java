package com.ttd.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.ttd.auth.AuthType;
import com.ttd.auth.Authentication;
import com.ttd.controller.base.CommonController;
import com.ttd.utils.EncryptUtils;
import com.ttd.utils.IntegerUtils;
import com.ttd.utils.RedisUtil;
import com.ttd.utils.ServletContextUtil;

/**
 * 自定义拦截器示例
 * 
 */
public class GlobalInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ServletContextUtil context = ServletContextUtil.getContext();
		// 设置本地线程变量
		context.setRequest(request);
		context.setResponse(response);
		HandlerMethod method = (HandlerMethod) handler;

		Object[] params = new Object[] { request.getRequestURI(), request.getParameterMap() };
		logger.info("request：请求地址：{}-请求参数-{}", params);

		boolean ret = authentication(method.getMethodAnnotation(Authentication.class), context);
		if (ret) {
			refreHash(context, false);
		}
		return ret;
	}

	@Override
	public void postHandle(HttpServletRequest request,	HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		// System.out.println(">>>MyInterceptor1>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception ex) throws Exception {
		ServletContextUtil context = ServletContextUtil.getContext();
		refreHash(context, true);
	}

	private void refreHash(ServletContextUtil context, boolean refree) {
		HttpServletRequest request = context.getRequest();
		String mac = context.getParameter("mac");
		String os = context.getParameter("os");
		String version = context.getParameter("version");
				
		String hashKey = EncryptUtils.encodeMD532(mac + os + version, null);
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()); 
		RedisUtil redisUtil = (RedisUtil) factory.getBean("redisUtil");
		
		if (refree) {
			if (redisUtil.exists(hashKey)) { 
				redisUtil.remove(hashKey);
				logger.info("|----------释放hash-----------");
			}
		} else {
			redisUtil.set(hashKey, true);
			logger.info("|----------存储hash-----------");
		}
	}
	
	/**
	 * 校验token
	 * 
	 * @param request
	 * @param response
	 * @param auth
	 * @return
	 * @throws IOException
	 */
	private boolean authentication(Authentication auth,	ServletContextUtil context) throws IOException {
		HttpServletRequest request = context.getRequest();
		
		if (request.getParameterMap().containsKey("debug") || (auth != null && auth.type() == AuthType.NONE)) {
			return true;
		} else if (auth != null && auth.type() == AuthType.OPENAPI) {// 所有接口都需要签名验证
			return verifyAPI(context);
		} else if (auth != null && auth.type() == AuthType.NEEDLOGIN) {// 需要用户登录
			String uid = request.getParameter("userId");
			
			if (StringUtils.isBlank(uid) || !IntegerUtils.greatThanZero(IntegerUtils.parseInt(uid))) {
				BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()); 
				CommonController commomnController = (CommonController) factory.getBean("commonController");
				commomnController.kickOut("userId为必填项...");
				return false;
			}

			return verifyAPI(context);
		} else {
			return verifyAPI(context);
		}
	}

	/**
	 * 开放校验
	 * 
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 * @throws IOException
	 */
	private boolean verifyAPI(ServletContextUtil context) throws IOException {
		HttpServletRequest request = context.getRequest();
		
		String mac = context.getParameter("mac");
		String os = context.getParameter("os");
		String version = context.getParameter("version");

		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()); 
		RedisUtil redisUtil = (RedisUtil) factory.getBean("redisUtil");
		CommonController commomnController = (CommonController) factory.getBean("commonController");
				
		// 参数必传
		if (StringUtils.isBlank(mac) || StringUtils.isBlank(os)	|| StringUtils.isBlank(version)) {
			commomnController.kickOut("mac、os、version为必填项...");
			return false;
		}

		String hashKey = EncryptUtils.encodeMD532(mac + os + version, null);
		if (redisUtil.exists(hashKey)) {
			commomnController.kickOut("请求正在处理中，请稍候...");
			return false;
		} 
		return true;
	}

}
