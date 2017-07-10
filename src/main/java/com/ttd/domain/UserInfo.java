package com.ttd.domain;

/**
 * user_info
 * 
 * @author wolf
 */
public class UserInfo {
	private static final long serialVersionUID = 1L;

	private Long userId; // 用户id
	private String userName; // 登录名
	private String mobile; // 手机号（验证通过）
	private Integer houseId; //
	private java.util.Date created; //

	public UserInfo() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

	public java.util.Date getCreated() {
		return created;
	}

	public void setCreated(java.util.Date created) {
		this.created = created;
	}

}
