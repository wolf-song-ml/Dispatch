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
import com.ttd.domain.ForemanInfo;
import com.ttd.domain.base.Message;
import com.ttd.domain.base.Page;
import com.ttd.service.ForemanInfoService;
import com.ttd.utils.LongUtils;

/**
 * Controller
 * @author fwen
 * @since 2016-04-07
 */
@Controller("foremanInfoController")
@RequestMapping(value = "/foremanInfo", method = {RequestMethod.GET, RequestMethod.POST})
public class ForemanInfoController extends BaseController{ 
	 
	@Resource private ForemanInfoService foremanInfoService;
	
	/**
 	 * @return
 	 * @throws Exception
 	 */
// 	@ActionControllerLog(channel="web",action="foremanInfo",title="打开列表",isSaveRequestData=true)
 	@RequestMapping
    @Authentication(type = AuthType.OPENAPI, code = "order_confirm")
    public ModelAndView content() throws Exception {
 		
// 		return toResult("foremanInfo/foremanInfo", null);
 		return null;
    }
	
//	@ActionControllerLog(channel="web",action="foremanInfo",title="获取列表数据",isSaveRequestData=true)
 	@RequestMapping(value = "/list")
    @Authentication(type = AuthType.OPENAPI, code = "foremanInfoList")
    public ModelAndView list(Page<ForemanInfo> page, ForemanInfo foremanInfo) {	 		
	 	Message message = Message.success();
	 	// 分页查询
 	 	page = foremanInfoService.selectPage(foremanInfo, page);
 	 	// 设置查询结果
 	/* 	message.setItems(page.getResult());
 	 	message.setPageNum(page.getPageNum());
 	 	message.setPageSize(page.getPageSize());
 	 	message.setTotalCount(page.getTotalCount());
 	 	*/
	 	return toJSON(message);
    }
     	
 	/**
 	 * 保存信息
 	 * @return
 	 */
// 	@ActionControllerLog(channel="web",action="foremanInfo",title="保存数据",isSaveRequestData=true)
 	@RequestMapping(value = "/save")
    @Authentication(type = AuthType.OPENAPI, code = "foremanInfoList")
    public ModelAndView save(ForemanInfo foremanInfo) {
	 	int result = 0;
 		
 		try {
			/*if(LongUtils.greatThanZero(foremanInfo.getForemanInfoId())){
				foremanInfo.setUpdator(getLoginUser().getId());
				foremanInfo.setUpdated(new Date());
				result = foremanInfoService.updateByKey(foremanInfo);
			} else {
				foremanInfo.setCreator(getLoginUser().getId());
				foremanInfo.setUpdator(getLoginUser().getId());
				result = foremanInfoService.insertEntry(foremanInfo);
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
// 	@ActionControllerLog(channel="web",action="foremanInfo",title="删除数据",isSaveRequestData=true)
 	@RequestMapping(value = "/del-{id}", method = {RequestMethod.DELETE})
    @Authentication(type = AuthType.OPENAPI, code = "foremanInfoList")
    public ModelAndView del(@PathVariable(value = "id") Long foremanInfoId) {
 		
 		int result = foremanInfoService.deleteByKey(foremanInfoId);
 		
	 	return toJSON(result > 0 ? Message.success("删除成功！") : Message.failure("删除失败，请检查输入或联系管理员！"));
    }
    
    /**
 	 * 查看信息
 	 * @return
 	 */
// 	@ActionControllerLog(channel="web",action="foremanInfo",title="查看详情",isSaveRequestData=true)
 	@RequestMapping(value = "/view-{id}")
    @Authentication(type = AuthType.OPENAPI, code = "foremanInfoList")
    public ModelAndView view(@PathVariable(value = "id") Long foremanInfoId) {
	 	
	 	LOGGER.info("查看{}信息", foremanInfoId);
 		if(!LongUtils.greatThanZero(foremanInfoId)){
 			return toJSON(Message.failure("ID不合法！"));
 		}
 		
 		ForemanInfo foremanInfo = foremanInfoService.selectEntry(foremanInfoId);
 		if(foremanInfo == null){
 			return toJSON(Message.failure("未查询到相应信息！"));
 		}
 		
 		Message result = Message.success();
// 		result.setData(foremanInfo);
 		
	 	return toJSON(result);
    }
}
