package com.ttd.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Test;

public class TestCommon {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException {
		String in = TestCommon.class.getResource("/import/house_example.xls").getPath();
		InputStream is = TestCommon.class.getResourceAsStream("/import/house_example.xls");
		
		System.out.println(in);
	
	}

}
