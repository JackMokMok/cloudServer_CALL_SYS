package com.cloudapp.core.entity;

import com.cloudapp.core.entity.base.Entity;

/**
 * 角色
 * @author SHUBEN
 *
 */

public class Role implements Entity {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name;
	
	private String[] permissions;

	public Role() {
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
	
	public String[] getPermissions() {
		return permissions;
	}
	
	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}
}