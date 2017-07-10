package com.ttd.utils;


import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;

/**
 * 对象工具类
 * @author wolf
 * @since 2016-03-10
 */
public class ObjectUtil {

	protected final static Logger LOGGER = LoggerFactory.getLogger(ObjectUtil.class);

	/**
	 * 对象转JSON
	 * @param object
	 * @return
	 */
	public static String object2json(Object object) {
		if (object == null) {
			return null;
		}
		// 设置参数用于属性是null时也返回数据
		return JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * bean转map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object obj) { 
		Map<String, Object> params = Maps.newHashMap(); 
        try { 
        	PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
            for (int i = 0; i < descriptors.length; i++) { 
                String name = descriptors[i].getName(); 
                if (!"class".equals(name)) { 
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                } 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return params; 
	}
	
	/**
	 * 复制对象
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public static <T> T cpoy(Object obj) {
	    try {
	        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	        ObjectOutputStream out= new ObjectOutputStream(byteOut);
	        out.writeObject(obj);
	        
	        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
	        ObjectInputStream in = new ObjectInputStream(byteIn);
	        return (T)in.readObject();
        } catch (Exception e) {
            LOGGER.warn("对象拷贝失败",e.getMessage());
        }
	    return null;
	}
	
	/**
     * 复制对象
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T revert(byte[] bytes) {
        try {
            if(bytes == null || bytes.length == 0) {return null;}
            ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(byteIn);
            return (T)in.readObject();
        } catch (Exception e) {
            LOGGER.warn("对象拷贝失败",e.getMessage());
        }
        return null;
    }
}
