package com.cloudapp.core.entity;

import com.cloudapp.core.constants.OnlineConstant;
import com.cloudapp.core.entity.base.Entity;

public class Expand implements Entity {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	/**
	 * 场所id
	 */
	private Integer customerId;
	
	/**
	 * 关联组id
	 */
	private Integer callerGroupId;
	
	/**
	 * mac地址
	 */
	private String mac;
	
	
	/**
	 * 类型代码
	 */
	private String code;
	
	/**
	 * 设备名称
	 */
	private String name;

	/**
	 * 设备配置
	 */
	private String conf;
	
	/**
	 * 应用版本
	 */
	private Integer appVersion;
	
	/**
	 * 类型名称
	 */
	private String categoryName;
	
	/**
	 * 分组名称
	 */
	private String groupName;
	
	
	/**
	 * 离线时间
	 */
	private Long offTime;
	
	/**
	 * 是否在线，0为离线，1为在线
	 */
	private Integer online;
	
	
	public Expand() {
	}

	public Expand(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getMac() {
		if(mac != null) {
			return mac.toUpperCase();
		} else {
			return null;
		}
	}
	
	public void setMac(String mac) {
		if(mac != null) {
			mac = mac.toUpperCase();
		}
		this.mac = mac == null ? null : mac.trim();
	}
	
	public String getConf() {
		return conf;
	}
	
	public void setConf(String conf) {
		this.conf = conf;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getAppVersion() {
		return appVersion;
	}
	
	public void setAppVersion(Integer appVersion) {
		this.appVersion = appVersion;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public Integer getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	public Integer getCallerGroupId() {
		return callerGroupId;
	}
	
	public void setCallerGroupId(Integer callerGroupId) {
		this.callerGroupId = callerGroupId;
	}
	
	public Long getOffTime() {
		return offTime;
	}
	
	public void setOffTime(Long offTime) {
		this.offTime = offTime;
	}
	
	public Integer getOnline() {
		return online;
	}
	
	public void setOnline(Integer online) {
		this.online = online;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * 获取该设备是否在线
	 */
	public String getOnlineName() {		//小于等于180秒算在线
		if (this.offTime != null && this.offTime <= 180)
			return  OnlineConstant.ONLINE.getName();
		return OnlineConstant.OFFLINE.getName();
	}
}
