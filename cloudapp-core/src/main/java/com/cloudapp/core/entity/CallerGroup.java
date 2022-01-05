package com.cloudapp.core.entity;

import com.cloudapp.core.entity.base.Entity;

/**
 * 设备组
 *
 */

public class CallerGroup implements Entity {

	private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 * 场所id
	 */
	private Integer customerId;

	/**
	 * 呼叫器分组名称
	 */
	private String name;

	private Integer[] callerIds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name.trim();
	}
	
	public Integer getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer[] getCallerIds() {
		return callerIds;
	}

	public void setCallerIds(Integer[] callerIds) {
		this.callerIds = callerIds;
	}
}