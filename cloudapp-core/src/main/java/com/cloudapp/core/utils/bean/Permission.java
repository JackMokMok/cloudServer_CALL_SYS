package com.cloudapp.core.utils.bean;
import java.io.Serializable;

import com.cloudapp.core.utils.PermissionUtil;
import com.cloudapp.lib.xml.XmlProperty;
import com.cloudapp.lib.xml.XmlProperty.XmlPropertyType;

public class Permission implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private String name;

	/**
	 * 权限码
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private String code;


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

	public void addItem(String path) {
		PermissionUtil.getPath(path).addPermission(this);
	}
}
