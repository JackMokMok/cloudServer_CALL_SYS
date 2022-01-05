package com.cloudapp.core.utils.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cloudapp.lib.xml.XmlProperty;
import com.cloudapp.lib.xml.XmlProperty.XmlPropertyType;

public class PermissionGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private String name;

	/**
	 * 可见标识，用于角色的权限分配中是否展示
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private boolean visible = true;

	private List<Permission> permissions = new ArrayList<Permission>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		if (visible != null && "0".equals(visible))
			this.visible = false;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

}
