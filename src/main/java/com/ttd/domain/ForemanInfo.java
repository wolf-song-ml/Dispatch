package com.ttd.domain;


/**
 * foreman_info
 * 
 * @author wolf
 */
public class ForemanInfo {
	private static final long serialVersionUID = 1L;

	private Long foremanId; //
	private String foremanName; //
	private Integer score; //
	private Integer orderNum; //
	private Integer complainNum; //
	private Integer state; //
	private java.util.Date created; //

	public ForemanInfo() {
		super();
	}

	public Long getForemanId() {
		return foremanId;
	}

	public void setForemanId(Long foremanId) {
		this.foremanId = foremanId;
	}

	public String getForemanName() {
		return foremanName;
	}

	public void setForemanName(String foremanName) {
		this.foremanName = foremanName;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getComplainNum() {
		return complainNum;
	}

	public void setComplainNum(Integer complainNum) {
		this.complainNum = complainNum;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public java.util.Date getCreated() {
		return created;
	}

	public void setCreated(java.util.Date created) {
		this.created = created;
	}

}
