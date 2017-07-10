package com.ttd.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.google.common.collect.Maps;
import com.ttd.utils.AESUtils;

/**
 * 过滤器
 * 
 * @author Administrator
 * 
 */
@WebFilter(filterName = "webAPPFilter", urlPatterns = "/*")
public class WebAPPFilter implements Filter {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private final String iv = DigestUtils.sha256Hex("Totodi!@#").substring(0, 16);

	@Override
	public void destroy() {

	}
	
	private void handleParamter(String queryString, Map<String, String[]> retParams) {
		String[] params = queryString.split("&");
		for (int i = 0; i < params.length; i++) {
			int splitIndex = params[i].indexOf("=");
			if (splitIndex == -1) {
				continue;
			}
			String key = params[i].substring(0, splitIndex);

			if (splitIndex < params[i].length()) {
				String value = params[i].substring(splitIndex + 1);
				retParams.put(key, new String[] {value});
			}
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,	FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;

		// 解决跨域访问
		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Methods", "GET");
		res.addHeader("Access-Control-Allow-Methods", "POST");
		res.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");

		// 判断是否OPTIONS请求
		if (req.getMethod().equals("OPTIONS")) {
			res.setStatus(HttpStatus.OK.value());
			return;
		} else {
			try {
				 Map<String, String[]> retParams = Maps.newHashMap();
				 
				/* Map<String, String[]> params = new HashMap<String,String[]>(request.getParameterMap());
				 for (Entry<String, String[]> item : params.entrySet()) {
					 logger.info(">>>拦截前参数："+item.getValue()[0]);
					 String newVal = AESUtils.decrypt(item.getValue()[0], iv);
					 logger.info(">>>拦截后参数："+newVal);	
					 handleParamter(newVal, retParams);
				 } */
				 
				 byte[] buf = new byte[1024];
				 while (req.getInputStream().readLine(buf, 0, 1024) > 0) {
					 String tem = new String(buf);
					 logger.info(">>>拦截前参数：" + tem);
					 String newVal = AESUtils.decrypt(tem, iv);
					 logger.info(">>>拦截后参数：" + newVal);	
					 handleParamter(newVal, retParams);
				}
				 
				req = new  ParameterRequestWrapper((HttpServletRequest)req, retParams);
				chain.doFilter(req, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
