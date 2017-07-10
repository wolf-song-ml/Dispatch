package com.ttd.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ttd.domain.base.Page;
import com.ttd.exception.AppException;
import com.ttd.mapper.BaseMapper;
import com.ttd.utils.LongUtils;

/**
 * service基类<实体,主键>
 * 
 * @author fwen
 * @since 2016-03-10
 */
public abstract class BaseService<T, KEY extends Serializable> {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * 获取DAO操作类
	 */
	public abstract BaseMapper<T, KEY> getMapper();

	public int insertEntry(T t) {
		if (t == null) {
			return 0;
		}
		return getMapper().insertEntry(t);
	}

	public int insertEntry(List<T> list) {
		if (list == null || list.size() <= 0) {
			return 0;
		}
		int count = 0;
		for (T t : list) {
			count += getMapper().insertEntry(t);
		}
		return count;
	}

	public int deleteByKey(KEY key) {
		return getMapper().deleteByKey(key);
	}

	public int deleteByKey(KEY[] key) {
		return getMapper().deleteByKey(key);
	}

	public int deleteByKey(T condtion) {
		return getMapper().deleteByKey(condtion);
	}

	public int updateByKey(T condtion) {
		if (condtion == null) {
			return 0;
		}
		return getMapper().updateByKey(condtion);
	}

	public boolean saveOrUpdate(T t) {
		Long id = 0L;
		try {
			Class<?> clz = t.getClass();
			id = (Long) clz.getMethod("getId").invoke(t);
		} catch (Exception e) {
			LOGGER.warn("获取对象主键值失败!");
		}
		if (LongUtils.greatThanZero(id)) {
			return updateByKey(t) > 0;
		}
		return insertEntry(t) > 0;
	}

	public T selectEntry(KEY key) {
		return getMapper().selectEntry(key);
	}

	public T selectEntryOne(T condtion) {
		List<T> list = getMapper().selectEntryList(condtion);
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}

	public List<T> selectEntryList(T condtion) {
		return getMapper().selectEntryList(condtion);
	}

	public List<T> selectSimpleEntryList(T condtion) {
		return getMapper().selectSimpleEntryList(condtion);
	}

	public Page<T> selectPage(T condtion, Page<T> page) {
		Integer size = getMapper().selectEntryListCount(condtion);
		if (size == null || size <= 0) { // 查询结果为空
			page.setPageNum(1);
			return page;
		}
		page.setTotalCount(size);
		// 当前页面比总页面还大，设置回到第一页
		if (page.getPageNum() > page.getPageCount()) {
			page.setPageNum(1);
		}
		try {
			Class<?> clz = condtion.getClass();
			clz.getMethod("setStartIndex", Integer.class).invoke(condtion,
					page.getStartIndex());
			clz.getMethod("setEndIndex", Integer.class).invoke(condtion,
					page.getEndIndex());
		} catch (Exception e) {
			throw new AppException("设置分页参数失败", e);
		}
		page.setResult(selectEntryList(condtion));
		return page;
	}

	/**
	 * 查询数据总数
	 * 
	 * @param condtion
	 * @return
	 */
	public Integer selectEntryListCount(T condtion) {
		Integer size = getMapper().selectEntryListCount(condtion);

		return size;
	}

	/**
	 * 限制字符串长度
	 * 
	 * @param s
	 * @param maxLength
	 * @return
	 */
	public String limitStringLength(String s, int maxLength) {
		if (s == null)
			return "";
		if (s.length() > maxLength) {
			return s.substring(0, maxLength);
		}
		return s;
	}
}
