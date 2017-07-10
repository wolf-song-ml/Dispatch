package com.ttd.domain;


/**
 * log_order_operation
 * 
 * @author wolf
 */
public class LogOrderOperation {
	private static final long serialVersionUID = 1L;

	private Integer logId; //
	private String action; //
	private String params; //
	private java.util.Date created; //
	private Long userId; //

	public LogOrderOperation() {
		super();
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public java.util.Date getCreated() {
		return created;
	}

	public void setCreated(java.util.Date created) {
		this.created = created;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
