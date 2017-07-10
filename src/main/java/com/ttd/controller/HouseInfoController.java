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
import com.ttd.domain.HouseInfo;
import com.ttd.domain.base.Message;
import com.ttd.domain.base.Page;
import com.ttd.service.HouseInfoService;
import com.ttd.utils.LongUtils;

/**
 * Controller
 * @author fwen
 * @since 2016-04-07
 */
@Controller("houseInfoController")
@RequestMapping(value = "/houseInfo", method = {RequestMethod.GET, RequestMethod.POST})
public class HouseInfoController extends BaseController{ 
	 
	@Resource private HouseInfoService houseInfoService;
	
	/**
 	 * @return
 	 * @throws Exception
 	 */
// 	@ActionControllerLog(channel="web",action="houseInfo",title="打开列表",isSaveRequestData=true)
 	@RequestMapping
    @Authentication(type = AuthType.OPENAPI, code = "order_confirm")
    public ModelAndView content() throws Exception {
 		
// 		return toResult("houseInfo/houseInfo", null);
 		return null;
    }
	
//	@ActionControllerLog(channel="web",action="houseInfo",title="获取列表数据",isSaveRequestData=true)
 	@RequestMapping(value = "/list")
    @Authentication(type = AuthType.OPENAPI, code = "houseInfoList")
    public ModelAndView list(Page<HouseInfo> page, HouseInfo houseInfo) {	 		
	 	Message message = Message.success();
	 	// 分页查询
 	 	page = houseInfoService.selectPage(houseInfo, page);
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
// 	@ActionControllerLog(channel="web",action="houseInfo",title="保存数据",isSaveRequestData=true)
 	@RequestMapping(value = "/save")
    @Authentication(type = AuthType.OPENAPI, code = "houseInfoList")
    public ModelAndView save(HouseInfo houseInfo) {
	 	int result = 0;
 		
 		try {
			/*if(LongUtils.greatThanZero(houseInfo.getHouseInfoId())){
				houseInfo.setUpdator(getLoginUser().getId());
				houseInfo.setUpdated(new Date());
				result = houseInfoService.updateByKey(houseInfo);
			} else {
				houseInfo.setCreator(getLoginUser().getId());
				houseInfo.setUpdator(getLoginUser().getId());
				result = houseInfoService.insertEntry(houseInfo);
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
// 	@ActionControllerLog(channel="web",action="houseInfo",title="删除数据",isSaveRequestData=true)
 	@RequestMapping(value = "/del-{id}", method = {RequestMethod.DELETE})
    @Authentication(type = AuthType.OPENAPI, code = "houseInfoList")
    public ModelAndView del(@PathVariable(value = "id") Long houseInfoId) {
 		
 		int result = houseInfoService.deleteByKey(houseInfoId);
 		
	 	return toJSON(result > 0 ? Message.success("删除成功！") : Message.failure("删除失败，请检查输入或联系管理员！"));
    }
    
    /**
 	 * 查看信息
 	 * @return
 	 */
// 	@ActionControllerLog(channel="web",action="houseInfo",title="查看详情",isSaveRequestData=true)
 	@RequestMapping(value = "/view-{id}")
    @Authentication(type = AuthType.OPENAPI, code = "houseInfoList")
    public ModelAndView view(@PathVariable(value = "id") Long houseInfoId) {
	 	
	 	LOGGER.info("查看{}信息", houseInfoId);
 		if(!LongUtils.greatThanZero(houseInfoId)){
 			return toJSON(Message.failure("ID不合法！"));
 		}
 		
 		HouseInfo houseInfo = houseInfoService.selectEntry(houseInfoId);
 		if(houseInfo == null){
 			return toJSON(Message.failure("未查询到相应信息！"));
 		}
 		
 		Message result = Message.success();
// 		result.setData(houseInfo);
 		
	 	return toJSON(result);
    }
}
