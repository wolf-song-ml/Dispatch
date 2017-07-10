package com.ttd.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 实现CommandLineRunner接口可以在服务启动的时候执行一些任务。
 *
 */
@Component
@Order(value=2)
public class MyStartupRunner1 implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(MyStartupRunner1.class);
	
//	@Autowired
//	private StudentService stuService;

//	@Autowired
//	private IScoreDao scoreDao;

	@Override
	public void run(String... args) throws Exception {
		logger.info(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作 11111111 <<<<<<<<<<<<<");
		
//		logger.info("测试这里可以访问数据库：" + stuService.getList().size() + "，"+ scoreDao.getList().size());
		
		// 指定数据源
		//logger.info("指定数据源Ds1 >>> " + stuService.getListByDs1().get(0).getName());
		//logger.info("指定数据源Ds2 >>> " + stuService.getListByDs2().get(0).getName());
	} 

}
