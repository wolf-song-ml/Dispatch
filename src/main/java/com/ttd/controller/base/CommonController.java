package com.ttd.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ttd.auth.AuthType;
import com.ttd.auth.Authentication;
import com.ttd.domain.base.Message;

/**
 * 内部控制器不对外暴露
 * 
 * @author wolf
 */
@Controller("commonController")
@RequestMapping(value = "/common/", method = { RequestMethod.GET, RequestMethod.POST })
public class CommonController extends BaseController {

	/**
	 * 访问踢出去
	 * 
	 * @return
	 */
	@RequestMapping("kickOut")
	@Authentication(type = AuthType.NONE, code = "kickOut")
	public ModelAndView kickOut(String msg) {
		Message retMsg = Message.failure(msg);
		
		return toJSON(retMsg);
	}

	/**
	 * 需要登录跳转
	 * 
	 * @param location
	 */
	@RequestMapping("needLogin")
	@Authentication(type = AuthType.NONE, code = "login")
	public ModelAndView needLogin(String msg) {
		Message retMsg = Message.failure(msg);
		
		return toJSON(retMsg);
	}
   
}
