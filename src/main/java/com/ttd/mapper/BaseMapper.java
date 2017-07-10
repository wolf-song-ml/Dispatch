package com.ttd.mapper;

import java.io.Serializable;
import java.util.List;

/**
 * dao基类<实体,主键>
 * 
 * @author wolf
 */
public interface BaseMapper<T, KEY extends Serializable> {

	/**
	 * 添加对象
	 * 
	 * @param t
	 * @return
	 */
	int insertEntry(T t);

	/**
	 * 删除对象,主键
	 * 
	 * @param key
	 * @return
	 */
	int deleteByKey(KEY key);

	/**
	 * 删除对象,主键集合
	 * 
	 * @param key
	 * @return
	 */
	int deleteByKey(KEY[] key);

	/**
	 * 删除对象,条件
	 * 
	 * @param t
	 * @return
	 */
	int deleteByKey(T t);

	/**
	 * 更新对象,条件主键
	 * 
	 * @param t
	 * @return
	 */
	int updateByKey(T t);

	/**
	 * 查询对象,条件主键
	 * 
	 * @param key
	 * @return
	 */
	T selectEntry(KEY key);

	/**
	 * 通过条件查询一个对象
	 * 
	 * @param condition
	 * @return
	 */
	T selectEntryOne(T condition);

	/**
	 * 查询对象,只要不为NULL与空则为条件
	 * 
	 * @param t
	 * @return
	 */
	List<T> selectEntryList(T t);

	/**
	 * 查询简化版的实例
	 * 
	 * @param t
	 * @return
	 */
	List<T> selectSimpleEntryList(T t);

	/**
	 * 查询对象总数
	 * 
	 * @param t
	 * @return
	 */
	Integer selectEntryListCount(T t);

}
