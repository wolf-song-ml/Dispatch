package com.ttd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * 处理静态资源URL
 *
 */
@ControllerAdvice
public class ResourceUrlProviderController {

	@Autowired
	private ResourceUrlProvider resourceUrlProvider;

	@ModelAttribute("urls")
	public ResourceUrlProvider urls() {
		System.out.println("》》》》》》》公共增加urls resource");
		return this.resourceUrlProvider;
	}
}
