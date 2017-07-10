package com.ttd.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ttd.domain.ForemanInfo;
import com.ttd.mapper.BaseMapper;
import com.ttd.mapper.ForemanInfoMapper;

/**
 * ForemanInfoService 实现类
 * 
 * @author wolf
 */
@Service("foremanInfoService")
public class ForemanInfoService extends BaseService<ForemanInfo, Long> {

	@Resource
	private ForemanInfoMapper foremanInfoMapper;

	public BaseMapper<ForemanInfo, Long> getMapper() {
		return foremanInfoMapper;
	}
}