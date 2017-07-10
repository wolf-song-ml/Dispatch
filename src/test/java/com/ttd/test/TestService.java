package com.ttd.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.opensymphony.util.ClassLoaderUtil;
import com.ttd.domain.HouseInfo;
import com.ttd.mapper.HouseInfoMapper;
import com.ttd.utils.ExcelUtils;
import com.ttd.utils.RedisUtil;
import com.ttd.utils.ServletContextUtil;

import freemarker.template.Template;

public class TestService extends BaseTestService {
	@Autowired
	JavaMailSender mailSender;
	@Autowired
	FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private CacheManager cacheManager;
	
	private static final Logger logger = LoggerFactory.getLogger(TestService.class);
	
	@Autowired ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired private HouseInfoMapper houseInfoMapper;
	
	@Autowired private RedisUtil redisUtil;

	@Test
	public void testSys() {
//		System.out.println(studentService.getList().toString());
	}

	@Test
	public void testMail() {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
			Template template = freeMarkerConfigurer.getConfiguration().getTemplate("mail.ftl");// 加载资源文件
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("key1", "key1");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			helper.setFrom("tech@shulaibao.com");// 发送者.
			helper.setTo("songjun@totodi.com");// 接收者.
			helper.setSubject("测试邮件（邮件主题）");// 邮件主题.
			helper.setText(html, true);

			mailSender.send(mimeMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testThread() {
		Callable<String> resultCall = new Callable<String>() {
			@Override
			public String call() {
				logger.info("ThreadName==:" + Thread.currentThread().getName());
				logger.info("######################分发合同开始#####################");
				return "success";
			}
		};
    	
		final Future<String> retFuture = taskExecutor.submit(resultCall);
	}
	
	
	@Test
	public void testCache() {
		System.out.println(cacheManager.getCacheNames());
	}
	
	@Test
	public void testExcel() throws FileNotFoundException {
    	InputStream in = TestService.class.getResourceAsStream("/import/house_example.xls");
    	//jar 
//		URL is = ClassLoaderUtil.getResource("/import/house_example.xls", getClass());
		
		List<List<Object>> list = ExcelUtils.readExcelExceptHead(in, true);
		for (List<Object> sheet : list) {
			HouseInfo house = new HouseInfo();
			house.setHouseName((String) sheet.get(2));
			house.setHouseAddress((String)sheet.get(6));
			house.setLat((String)sheet.get(27));
			house.setLng((String)sheet.get(28));
			house.setCreated(new Date());
			house.setAreaId(1);
			
			houseInfoMapper.insertEntry(house);
		}
	}
	
	@Test
	public void testRedis() {
		redisUtil.set("testKey", "spring:session:expirations:1499333280000");
		System.out.println(redisUtil.get("testKey"));
		System.out.println(redisUtil.exists("testKey"));
		
		
	}
	

}
