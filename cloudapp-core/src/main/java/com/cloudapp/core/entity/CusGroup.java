package com.cloudapp.core.entity;

import com.cloudapp.core.entity.base.Entity;

/**
 * 场所组
 *
 */

public class CusGroup implements Entity {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name;
	
	private Integer[] cusIds;

	public CusGroup() {
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
		this.name = name;
	}
	
	public Integer[] getCusIds() {
		return cusIds;
	}
	
	public void setCusIds(Integer[] cusIds) {
		this.cusIds = cusIds;
	}
}