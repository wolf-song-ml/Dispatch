package com.ttd.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ttd.aspect.LogOrderAnnotation;
import com.ttd.auth.AuthType;
import com.ttd.auth.Authentication;
import com.ttd.config.MsgProducer;
import com.ttd.controller.base.BaseController;
import com.ttd.domain.OrderInfo;
import com.ttd.domain.base.Message;
import com.ttd.domain.base.Page;
import com.ttd.service.OrderInfoService;
import com.ttd.utils.LongUtils;

/**
 * Controller
 * 
 * @author wolf
 */
@RestController("orderInfoController")
@RequestMapping(value = "/orderInfo", method = { RequestMethod.GET, RequestMethod.POST })
public class OrderInfoController extends BaseController {

	@Resource private OrderInfoService orderInfoService;
	@Resource private MsgProducer msgProducer;
	
	/**
	 * @return
	 * @throws Exception
	 */
	// @ActionControllerLog(channel="web",action="orderInfo",title="打开列表",isSaveRequestData=true)
	@RequestMapping
	@Authentication(type = AuthType.OPENAPI, code = "order_confirm")
	public ModelAndView content() throws Exception {

		// return toResult("orderInfo/orderInfo", null);
		return null;
	}

	// @ActionControllerLog(channel="web",action="orderInfo",title="获取列表数据",isSaveRequestData=true)
	@RequestMapping(value = "/list")
	@Authentication(type = AuthType.OPENAPI, code = "orderInfoList")
	public ModelAndView list(Page<OrderInfo> page, OrderInfo orderInfo) {
		Message message = Message.success();
		// 分页查询
		page = orderInfoService.selectPage(orderInfo, page);
		// 设置查询结果
		/*
		 * message.setItems(page.getResult());
		 * message.setPageNum(page.getPageNum());
		 * message.setPageSize(page.getPageSize());
		 * message.setTotalCount(page.getTotalCount());
		 */

		return toJSON(message);
	}

	/**
	 * 保存信息
	 * 
	 * @return
	 */
	@LogOrderAnnotation(action="gennerateOrder")
	@RequestMapping(value = "/gennerateOrder")
	@Authentication(type = AuthType.OPENAPI, code = "gennerateOrder")
//	@Cacheable(key="retMsg")
	public Message gennerateOrder(OrderInfo cond) {
		Message retMsg = null;
		if (!LongUtils.greatThanZero(cond.getForemanId()) || !LongUtils.greatThanZero(cond.getHouseId()) ||
			!LongUtils.greatThanZero(cond.getUserId()) || !LongUtils.greatThanZero(cond.getTotalAmount())) {
			retMsg = Message.failure("非法生成订单");
			return retMsg;
		}
		
		//filter
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setUserId(cond.getUserId());
		orderInfo.setForemanId(cond.getForemanId());
		orderInfo.setHouseId(cond.getHouseId());
		orderInfo.setTotalAmount(cond.getTotalAmount());
		orderInfo.setCreated(new Date());
		
		if (orderInfoService.insertEntry(orderInfo) > 0) {
			retMsg = Message.success();
			msgProducer.send(JSON.toJSONString(orderInfo));
		} else {
			retMsg = Message.failure("订单生成失败");
		}
		return  retMsg;
	}

	/**
	 * 删除信息
	 * 
	 * @return
	 */
	// @ActionControllerLog(channel="web",action="orderInfo",title="删除数据",isSaveRequestData=true)
	@RequestMapping(value = "/del-{id}", method = { RequestMethod.DELETE })
	@Authentication(type = AuthType.OPENAPI, code = "orderInfoList")
	public ModelAndView del(@PathVariable(value = "id") Long orderInfoId) {

		int result = orderInfoService.deleteByKey(orderInfoId);

		return toJSON(result > 0 ? Message.success("删除成功！") : Message
				.failure("删除失败，请检查输入或联系管理员！"));
	}

	/**
	 * 查看信息
	 * 
	 * @return
	 */
	// @ActionControllerLog(channel="web",action="orderInfo",title="查看详情",isSaveRequestData=true)
	@RequestMapping(value = "/view-{id}")
	@Authentication(type = AuthType.OPENAPI, code = "orderInfoList")
	public ModelAndView view(@PathVariable(value = "id") Long orderInfoId) {

		LOGGER.info("查看{}信息", orderInfoId);
		if (!LongUtils.greatThanZero(orderInfoId)) {
			return toJSON(Message.failure("ID不合法！"));
		}

		OrderInfo orderInfo = orderInfoService.selectEntry(orderInfoId);
		if (orderInfo == null) {
			return toJSON(Message.failure("未查询到相应信息！"));
		}

		Message result = Message.success();
		// result.setData(orderInfo);

		return toJSON(result);
	}
}
