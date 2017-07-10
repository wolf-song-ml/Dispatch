package com.ttd.domain;

/**
 * order_info
 * 
 * @author wolf
 */
public class OrderInfo {
	private static final long serialVersionUID = 1L;

	private Long orderId; //
	private Long userId; // 用户ID（包销公司账户ID）
	private Long foremanId; //
	private Long totalAmount; //
	private java.util.Date created; //
	private Long houseId;

	public OrderInfo() {
		super();
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getForemanId() {
		return foremanId;
	}

	public void setForemanId(Long foremanId) {
		this.foremanId = foremanId;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public java.util.Date getCreated() {
		return created;
	}

	public void setCreated(java.util.Date created) {
		this.created = created;
	}

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}
	
}
