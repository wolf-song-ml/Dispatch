package com.ttd.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ttd.domain.HouseInfo;
import com.ttd.mapper.BaseMapper;
import com.ttd.mapper.HouseInfoMapper;

/**
 * HouseInfoService 实现类
 * 
 * @author wolf
 */
@Service("houseInfoService")
public class HouseInfoService extends BaseService<HouseInfo, Long> {

	@Resource
	private HouseInfoMapper houseInfoMapper;

	public BaseMapper<HouseInfo, Long> getMapper() {
		return houseInfoMapper;
	}
}