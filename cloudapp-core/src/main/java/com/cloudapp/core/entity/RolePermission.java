package com.cloudapp.core.entity;

import com.cloudapp.core.entity.base.Entity;

/**
 * 角色权限
 * @author SHUBEN
 *
 */

public class RolePermission implements Entity {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	/**
	 * 角色id
	 */
	private Integer roleId;

	/**
	 * 权限
	 */
	private String permission;

	public RolePermission() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}