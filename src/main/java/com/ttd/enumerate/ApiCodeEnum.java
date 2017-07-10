package com.ttd.enumerate;

import java.util.Map;

import com.google.common.collect.Maps;

public enum ApiCodeEnum {
	SUCCESS(0, "成功"), 
	NEED_LOGIN(1000, "需要登录"),
	NOT_FOUND(1001, "资源不存在"),
	INVALID_PARAMETER(1002, "非法参数"),
	NOT_AUTHORIZED(1003,"未授权"),
	SYSTEM_ABNORMAL(999, "系统异常"),
	FORCE_UPDATE(2000, "强制更新");

    private int value;
    private String name;

    public int getValue() {
        return value;
    }
    
    public String getName() {
		return name;
	}
    
    private ApiCodeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
    
    public static Map<String, Object> toMap(){
    	Map<String, Object> map = Maps.newHashMap();
    	map.put("0", SUCCESS.getValue());
    	map.put("1000", NEED_LOGIN.getValue());
    	map.put("1001", NOT_FOUND.getValue());
    	map.put("1002", INVALID_PARAMETER.getValue());
    	map.put("1003", NOT_AUTHORIZED.getValue());
    	map.put("2000", FORCE_UPDATE.getValue());
    	map.put("500", SYSTEM_ABNORMAL.getValue());
    	return map;
    }
}
