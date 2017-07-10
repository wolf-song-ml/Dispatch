package com.ttd.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ttd.domain.LogOrderOperation;
import com.ttd.mapper.BaseMapper;
import com.ttd.mapper.LogOrderOperationMapper;

/**
 * LogOrderOperationService 实现类
 * 
 * @author wolf
 */
@Service("logOrderOperationService")
public class LogOrderOperationService extends BaseService<LogOrderOperation, Long> {

	@Resource
	private LogOrderOperationMapper logOrderOperationMapper;

	public BaseMapper<LogOrderOperation, Long> getMapper() {
		return logOrderOperationMapper;
	}
	
}