package com.ttd.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ttd.domain.UserInfo;
import com.ttd.mapper.BaseMapper;
import com.ttd.mapper.UserInfoMapper;

/**
 * UserInfoService 实现类
 * 
 * @author wolf
 */
@Service("userInfoService")
public class UserInfoService extends BaseService<UserInfo, Long> {

	@Resource
	private UserInfoMapper userInfoMapper;

	public BaseMapper<UserInfo, Long> getMapper() {
		return userInfoMapper;
	}
}