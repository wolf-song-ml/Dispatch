package com.ttd.aspect;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.ttd.datasource.DynamicDataSourceAspect;
import com.ttd.datasource.DynamicDataSourceContextHolder;
import com.ttd.datasource.TargetDataSource;
import com.ttd.domain.LogOrderOperation;
import com.ttd.service.LogOrderOperationService;
import com.ttd.utils.LongUtils;
import com.ttd.utils.ServletContextUtil;

@Aspect
@Component
public class LogOrderAspect {
	private static final Logger logger = LoggerFactory.getLogger(LogOrderAspect.class);
	@Resource private LogOrderOperationService logOrderService;

	@Before("@annotation(orderAspect)")
	public void gennerateLog(JoinPoint point, LogOrderAnnotation orderAspect) throws Throwable {
		logger.info(">>>>>>订单操作开始aspect");
		ServletContextUtil context = ServletContextUtil.getContext();
		HttpServletRequest request = context.getRequest();
		
		LogOrderOperation log = new LogOrderOperation();
		log.setAction(orderAspect.action());
		log.setUserId(LongUtils.parseLong(request.getParameter("userId")));
		Map<String, String[]> params = Maps.newHashMap(request.getParameterMap());
		params.put("url", new String[]{request.getRequestURI()});
		log.setParams(JSON.toJSONString(params));
		log.setCreated(new Date());
		
		logOrderService.insertEntry(log);
	}

	@After("@annotation(orderAspect)")
	public void endLog(JoinPoint point, LogOrderAnnotation orderAspect) {
		logger.info(">>>>>>订单操作执行完aspect");
	}
	
	@AfterReturning("@annotation(orderAspect)")
	public void returnLog(JoinPoint point, LogOrderAnnotation orderAspect) {
		logger.info(">>>>>>订单操作执行完aspect，return.................");
	}
	
	
}
