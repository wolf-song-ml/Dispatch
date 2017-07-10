package com.ttd.domain.base;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ttd.enumerate.ApiCodeEnum;
import com.ttd.utils.IntegerUtils;

public class Message implements Serializable{
    private static final long serialVersionUID = 1L;
	private Integer code;// 返回状态码
	private String msg;   // 结果
	private DataObj data;  // 结果对象
	
	public Message() {
		super();
		this.code = ApiCodeEnum.SUCCESS.getValue();
		this.msg = ApiCodeEnum.SUCCESS.getName();
		this.data = new DataObj();
		this.data.setBean(new Object());
		this.data.setList(new ArrayList<>());
	}	
	
	public static Message success () {
		ApiCodeEnum code = ApiCodeEnum.SUCCESS;
		return new Message(code.getValue(), code.getName());
	}
	
	public static Message success (ApiCodeEnum code) {
		if (code == null) {
			code = ApiCodeEnum.SUCCESS;
		}
		return new Message(code.getValue(), code.getName());
	}
	
	public static Message success (String msg) {
		ApiCodeEnum code = ApiCodeEnum.SUCCESS;
		return new Message(code.getValue(), msg);
	}
	
	public static Message failure () {
		ApiCodeEnum	code = ApiCodeEnum.INVALID_PARAMETER;
		return new Message(code.getValue(), code.getName());
	}
	
	public static Message failure (ApiCodeEnum code) {
		if (code == null) {
			code = ApiCodeEnum.INVALID_PARAMETER;
		}
		return new Message(code.getValue(), code.getName());
	}

	public static Message failure (String msg) {
		ApiCodeEnum code = ApiCodeEnum.INVALID_PARAMETER;
		return new Message(code.getValue(), msg);
	}
	
	/**
	 * 自定义错误提示信息
	 * @param code
	 * @param message
	 * @return
	 */
	public static Message success (ApiCodeEnum code, String message) {
		if (code == null) {
			code = ApiCodeEnum.SUCCESS;
		}
		return new Message(code.getValue(), message);
	}
	
	/**
	 * 自定义错误提示信息
	 * @param code
	 * @param message
	 * @return
	 */
	public static Message failure (ApiCodeEnum code, String message) {
		if (code == null) {
			code = ApiCodeEnum.INVALID_PARAMETER;
		}
		return new Message(code.getValue(), message);
	}
	
	public Message(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = new DataObj();
		this.data.setBean("");
		this.data.setList(new ArrayList<>());
	}
	
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public DataObj getData() {
		return data;
	}
	
	public void setData(DataObj data) {
		this.data = data;
	}
	
	public void listPage(List<?> list, Integer total, Integer currentPage, Integer pageSize) {
		this.data.setList(list);
		this.data.setIsArray(2);
		if (IntegerUtils.greatThanZero(total)) {
			Integer totalPage =  (int) Math.ceil(total * 1.0 / pageSize * 1.0);
			if (totalPage < currentPage) {
				this.setCode(ApiCodeEnum.INVALID_PARAMETER.getValue());
				this.setMsg(ApiCodeEnum.INVALID_PARAMETER.getName());
				return ;
			}
			
			this.data.setTotal(total);
			this.data.setCurrentPage(currentPage);
			this.data.setPageSize(pageSize);
			this.data.setTotalPage(totalPage); 
		}
	}
	
	public void mergeBean(Object bean) {
		this.data.setBean(bean);
	}
	
	public class DataObj implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Integer isArray = 1;
		private Object bean;
		private List<?> list;
		private Integer total=0;
		private Integer totalPage=0;
		private Integer currentPage=0;
		private Integer pageSize = 10;
		
		public Integer getIsArray() {
			return isArray;
		}
		public void setIsArray(Integer isArray) {
			this.isArray = isArray;
		}
		public Object getBean() {
			return bean;
		}
		public void setBean(Object bean) {
			this.bean = bean;
		}
		public List<?> getList() {
			return list;
		}
		public void setList(List<?> list) {
			this.list = list;
		}
		public Integer getTotal() {
			return total;
		}
		public void setTotal(Integer total) {
			this.total = total;
		}
		public Integer getTotalPage() {
			return totalPage;
		}
		public void setTotalPage(Integer totalPage) {
			this.totalPage = totalPage;
		}
		public Integer getCurrentPage() {
			return currentPage;
		}
		public void setCurrentPage(Integer currentPage) {
			this.currentPage = currentPage;
		}
		public Integer getPageSize() {
			return pageSize;
		}
		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}		
		
	}

}	