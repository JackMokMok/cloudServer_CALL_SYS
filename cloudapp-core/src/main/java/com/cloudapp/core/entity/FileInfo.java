package com.cloudapp.core.entity;

import com.cloudapp.core.entity.base.Entity;

/**
 * 文件信息类
 * @author SHUBEN
 *
 */

public class FileInfo implements Entity {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String path;
	
	private String md5;
	
	public FileInfo() {
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMd5() {
		return md5;
	}
	
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}
