package com.cloudapp.core.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cloudapp.core.entity.base.Entity;

/**
 * 登录记录
 * @author SHUBEN
 *
 */

public class AdminLoginInfo implements Entity {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer adminId;

	
	/**
	 * 登录时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date loginTime;
	
	/**
	 * 登录IP
	 */
	private String loginIp;
	
	
	public AdminLoginInfo() {
	}

	public AdminLoginInfo(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAdminId() {
		return adminId;
	}
	
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	
	public Date getLoginTime() {
		return loginTime;
	}
	
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	public String getLoginIp() {
		return loginIp;
	}
	
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
}
