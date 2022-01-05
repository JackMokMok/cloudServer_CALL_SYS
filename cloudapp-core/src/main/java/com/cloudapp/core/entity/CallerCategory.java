package com.cloudapp.core.entity;

import com.cloudapp.core.entity.base.Entity;

/**
 * 设备类型
 *
 */

public class CallerCategory implements Entity {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer customerId;

	private String name;

	private Integer[] callerIds;

	public CallerCategory() {
	}

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