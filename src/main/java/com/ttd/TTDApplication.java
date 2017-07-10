package com.ttd;

import java.util.Date;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.ttd.datasource.DynamicDataSourceRegister;
import com.ttd.listener.ApplicationStartUpListener;
import com.ttd.utils.DateUtil;

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@Import(DynamicDataSourceRegister.class)
@EnableScheduling
public class TTDApplication extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(TTDApplication.class);

	@Bean
	public PlatformTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	//可以设置执行任务的线程池的数量。默认是单线程。
	@Bean
	public ThreadPoolTaskScheduler getDefaultThreadPoolScheduler(){
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(2);
		threadPoolTaskScheduler.setThreadNamePrefix("scheduleMoon");
		return threadPoolTaskScheduler;
	}
	
	@Bean
    public ThreadPoolTaskExecutor createThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(20);
        return threadPoolTaskExecutor;
    }


	/**
	 * 修改DispatcherServlet默认配置
	 *
	 * @param dispatcherServlet
	 * @return
	 */
	@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
		registration.getUrlMappings().clear();
		registration.addUrlMappings("*.do");
		registration.addUrlMappings("*.json");
		return registration;
	}
	
	private CorsConfiguration buildConfig() {  
        CorsConfiguration corsConfiguration = new CorsConfiguration();  
        corsConfiguration.addAllowedOrigin("*");  
        corsConfiguration.addAllowedHeader("*");  
        corsConfiguration.addAllowedMethod("*");  
        return corsConfiguration;  
    }  
      
    /** 
     * 跨域过滤器 
     * @return 
     */  
    @Bean  
    public CorsFilter corsFilter() {  
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
        source.registerCorsConfiguration("/**", buildConfig()); // 4  
        return new CorsFilter(source);  
    }  
	
	@Bean 
    public MultipartConfigElement multipartConfigElement() { 
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
        factory.setMaxFileSize("400MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("400MB"); 
        //Sets the directory location where files will be stored.
        //factory.setLocation("路径地址");
        return factory.createMultipartConfig(); 
    }  
	
	@Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
            	if(source.trim().length() == 16)
            		source = source + ":00";
                return DateUtil.stringToDate(source, "yyyy-MM-dd HH:mm:ss");
            }
        };
    }

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(TTDApplication.class);
		// 加载系统配置
		application.addListeners(new ApplicationStartUpListener());
		application.run(args);
	}
	
}
