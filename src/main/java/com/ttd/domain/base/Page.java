package com.ttd.domain.base;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * 
 * @author fwen
 */
public class Page<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private int pageSize = 10;
	private int total;
	private int pageNum;
	private List<T> data;

	public Page() {
		// 默认构造器
	}

	public Page(int currentPage) {
		this.pageNum = currentPage;
	}

	public Page(int currentPage, int pageSize) {
		this.pageNum = currentPage;
		this.pageSize = pageSize;
	}

	/**
	 * 获取开始索引
	 * 
	 * @return
	 */
	public int getStartIndex() {
		return (getPageNum() - 1) * this.pageSize;
	}

	/**
	 * 获取结束索引
	 * 
	 * @return
	 */
	public int getEndIndex() {
		return getPageNum() * this.pageSize;
	}

	/**
	 * 是否第一页
	 * 
	 * @return
	 */
	public boolean isFirstPage() {
		return getPageNum() <= 1;
	}

	/**
	 * 是否末页
	 * 
	 * @return
	 */
	public boolean isLastPage() {
		return getPageNum() >= getPageCount();
	}

	/**
	 * 获取下一页页码
	 * 
	 * @return
	 */
	public int getNextPage() {
		if (isLastPage()) {
			return getPageNum();
		}
		return getPageNum() + 1;
	}

	/**
	 * 获取上一页页码
	 * 
	 * @return
	 */
	public int getPreviousPage() {
		if (isFirstPage()) {
			return 1;
		}
		return getPageNum() - 1;
	}

	/**
	 * 获取当前页页码
	 * 
	 * @return
	 */
	public int getPageNum() {
		if (pageNum == 0) {
			pageNum = 1;
		}
		return pageNum;
	}

	/**
	 * 取得总页数
	 * 
	 * @return
	 */
	public int getPageCount() {
		if (total % pageSize == 0) {
			return total / pageSize;
		} else {
			return total / pageSize + 1;
		}
	}

	/**
	 * 取总记录数.
	 * 
	 * @return
	 */
	public int getTotalCount() {
		return this.total;
	}

	/**
	 * 设置当前页
	 * 
	 * @param currentPage
	 */
	public void setPageNum(int currentPage) {
		this.pageNum = currentPage;
	}

	/**
	 * 获取每页数据容量.
	 * 
	 * @return
	 */
	public int getPageSize() {
		if (pageSize > 500) {
			pageSize = 500;// 最大500条
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize > 500) {
			pageSize = 500;// 最大500条
		}
		this.pageSize = pageSize;
	}

	/**
	 * 该页是否有下一页.
	 * 
	 * @return
	 */
	public boolean hasNextPage() {
		return getPageNum() < getPageCount();
	}

	/**
	 * 该页是否有上一页.
	 * 
	 * @return
	 */
	public boolean hasPreviousPage() {
		return getPageNum() > 1;
	}

	/**
	 * 获取数据集
	 * 
	 * @return
	 */
	public List<T> getResult() {
		return data;
	}

	/**
	 * 设置数据集
	 * 
	 * @param data
	 */
	public void setResult(List<T> data) {
		this.data = data;
	}

	/**
	 * 设置总记录条数
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.total = totalCount;
	}

	// ==============扩展字段===============//
	private String unit = "条";// 单位
	private String extInfo;// 扩展信息

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnit() {
		return unit;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public String getExtInfo() {
		return extInfo;
	}
}
