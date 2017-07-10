package com.ttd.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 实现CommandLineRunner接口可以在服务启动的时候执行一些任务。
 *
 */
@Component
@Order(value=1)
public class MyStartupRunner2 implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作 22222222 <<<<<<<<<<<<<");
	}

}
