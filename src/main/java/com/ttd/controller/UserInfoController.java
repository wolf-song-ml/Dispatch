package com.ttd.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ttd.auth.AuthType;
import com.ttd.auth.Authentication;
import com.ttd.controller.base.BaseController;
import com.ttd.domain.UserInfo;
import com.ttd.domain.base.Message;
import com.ttd.domain.base.Page;
import com.ttd.service.UserInfoService;
import com.ttd.utils.LongUtils;

/**
 * Controller
 * @author fwen
 * @since 2016-04-07
 */
@Controller("userInfoController")
@RequestMapping(value = "/userInfo", method = {RequestMethod.GET, RequestMethod.POST})
public class UserInfoController extends BaseController { 
	 
	@Resource private UserInfoService userInfoService;
	
	/**
 	 * @return
 	 * @throws Exception
 	 */
// 	@ActionControllerLog(channel="web",action="userInfo",title="打开列表",isSaveRequestData=true)
 	@RequestMapping
    @Authentication(type = AuthType.OPENAPI, code = "order_confirm")
    public ModelAndView content() throws Exception {
 		
// 		return toResult("userInfo/userInfo", null);
 		return null;
    }
	
//	@ActionControllerLog(channel="web",action="userInfo",title="获取列表数据",isSaveRequestData=true)
 	@RequestMapping(value = "/list")
    @Authentication(type = AuthType.OPENAPI, code = "userInfoList")
    public ModelAndView list(Page<UserInfo> page, UserInfo userInfo) {	 		
	 	Message message = Message.success();
	 	// 分页查询
 	 	page = userInfoService.selectPage(userInfo, page);
 	 	// 设置查询结果
 	 /*	message.setItems(page.getResult());
 	 	message.setPageNum(page.getPageNum());
 	 	message.setPageSize(page.getPageSize());
 	 	message.setTotalCount(page.getTotalCount());*/
 	 	
	 	return toJSON(message);
    }
     	
 	/**
 	 * 保存信息
 	 * @return
 	 */
// 	@ActionControllerLog(channel="web",action="userInfo",title="保存数据",isSaveRequestData=true)
 	@RequestMapping(value = "/save")
    @Authentication(type = AuthType.OPENAPI, code = "userInfoList")
    public ModelAndView save(UserInfo userInfo) {
	 	int result = 0;
 		
 		try {
		/*	if(LongUtils.greatThanZero(userInfo.getUserInfoId())){
				userInfo.setUpdator(getLoginUser().getId());
				userInfo.setUpdated(new Date());
				result = userInfoService.updateByKey(userInfo);
			} else {
				userInfo.setCreator(getLoginUser().getId());
				userInfo.setUpdator(getLoginUser().getId());
				result = userInfoService.insertEntry(userInfo);
			}*/
		} catch (Exception e) {
			LOGGER.error("新增或修改自动任务出错：{}", e.toString(), e);
		}
 		
	 	return toJSON(result > 0 ? Message.success("保存信息成功！") : Message.failure("保存信息失败，请检查输入或联系管理员！"));
	 	
    }
 	
 	/**
 	 * 删除信息
 	 * @return
 	 */
// 	@ActionControllerLog(channel="web",action="userInfo",title="删除数据",isSaveRequestData=true)
 	@RequestMapping(value = "/del-{id}", method = {RequestMethod.DELETE})
    @Authentication(type = AuthType.OPENAPI, code = "userInfoList")
    public ModelAndView del(@PathVariable(value = "id") Long userInfoId) {
 		
 		int result = userInfoService.deleteByKey(userInfoId);
 		
	 	return toJSON(result > 0 ? Message.success("删除成功！") : Message.failure("删除失败，请检查输入或联系管理员！"));
    }
    
    /**
 	 * 查看信息
 	 * @return
 	 */
// 	@ActionControllerLog(channel="web",action="userInfo",title="查看详情",isSaveRequestData=true)
 	@RequestMapping(value = "/view-{id}")
    @Authentication(type = AuthType.OPENAPI, code = "userInfoList")
    public ModelAndView view(@PathVariable(value = "id") Long userInfoId) {
	 	
	 	LOGGER.info("查看{}信息", userInfoId);
 		if(!LongUtils.greatThanZero(userInfoId)){
 			return toJSON(Message.failure("ID不合法！"));
 		}
 		
 		UserInfo userInfo = userInfoService.selectEntry(userInfoId);
 		if(userInfo == null){
 			return toJSON(Message.failure("未查询到相应信息！"));
 		}
 		
 		Message result = Message.success();
// 		result.setData(userInfo);
 		
	 	return toJSON(result);
    }
}
