package com.cloudapp.core.entity;

import com.cloudapp.core.entity.base.Entity;

public class ExpandCategory implements Entity {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	/**
	 * app名称
	 */
	private String name;
	
	/**
	 * 识别码
	 */
	private String code;

	/**
	 * 默认配置
	 */
	private String defaultConf;
	
	
	public ExpandCategory() {
	}

	public ExpandCategory(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDefaultConf() {
		return defaultConf;
	}
	
	public void setDefaultConf(String defaultConf) {
		this.defaultConf = defaultConf;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}
