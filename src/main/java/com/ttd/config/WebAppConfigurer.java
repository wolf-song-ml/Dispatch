package com.ttd.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ttd.interceptor.GlobalInterceptor;
import com.ttd.interceptor.MyInterceptor2;

//@Repository
@Component
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter implements EnvironmentAware {

	private static final Logger logger = LoggerFactory.getLogger(WebAppConfigurer.class);

	private RelaxedPropertyResolver propertyResolver;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		registry.addInterceptor(new GlobalInterceptor()).addPathPatterns("/**").excludePathPatterns("/common/**");
		
//		registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 访问myres根目录下的fengjing.jpg 的URL为
		// http://localhost:8080/myres/fengjing.jpg 不影响Spring Boot的默认的 /**映射，可以同时使用。
		registry.addResourceHandler("/myres/**").addResourceLocations("classpath:/myres/");
		// 访问myres根目录下的fengjing.jpg 的URL为 http://localhost:8080/fengjing.jpg（/** 会覆盖系统默认的配置）
		// registry.addResourceHandler("/**").addResourceLocations("classpath:/myres/").addResourceLocations("classpath:/static/");

		// 可以直接使用addResourceLocations 指定磁盘绝对路径，同样可以配置多个位置，注意路径写法需要加上file:
		registry.addResourceHandler("/myimgs/**").addResourceLocations("file:H:/myimgs/");
		super.addResourceHandlers(registry);
	}

	/**
	 * 实现EnvironmentAware重写setEnvironment可以在工程启动时，获取系统环境变量和application配置的变量。
	 * 这个方法只是测试实现EnvironmentAware接口，读取环境变量的方法。
	 */
	@Override
	public void setEnvironment(Environment env) {
		propertyResolver = new RelaxedPropertyResolver(env,	"spring.datasource.");
		String url = propertyResolver.getProperty("url");
		logger.info(">>>>>>>" + url);
	}
	
}
