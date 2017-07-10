package com.ttd.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

/**
 * MyBatis 配置
 *
 */
@Configuration
@MapperScan(basePackages = "com.ttd.mapper")
public class MyBatisConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(MyBatisConfiguration.class);
	
	@Bean
	public PageHelper pageHelper(DataSource dataSource) {
		logger.info("注册MyBatis分页插件PageHelper");
		PageHelper pageHelper = new PageHelper();
		Properties p = new Properties();
		p.setProperty("offsetAsPageNum", "true");
		p.setProperty("rowBoundsWithCount", "true");
		p.setProperty("reasonable", "true");
		pageHelper.setProperties(p);
		return pageHelper;
	}
	
}
