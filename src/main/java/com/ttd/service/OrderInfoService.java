package com.ttd.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ttd.domain.OrderInfo;
import com.ttd.mapper.BaseMapper;
import com.ttd.mapper.OrderInfoMapper;

/**
 * OrderInfoService 实现类
 * 
 * @author wolf
 */
@Service("orderInfoService")
public class OrderInfoService extends BaseService<OrderInfo, Long> {

	@Resource
	private OrderInfoMapper orderInfoMapper;

	public BaseMapper<OrderInfo, Long> getMapper() {
		return orderInfoMapper;
	}
}