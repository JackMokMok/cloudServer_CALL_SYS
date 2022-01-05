package com.cloudapp.core.utils.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cloudapp.core.entity.Admin;
import com.cloudapp.lib.xml.XmlProperty;
import com.cloudapp.lib.xml.XmlProperty.XmlPropertyType;

public class PathGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private String name;

	/**
	 * 编码
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private String code;

	/**
	 * 是否在菜单中显示
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private boolean visible = false;

	private List<Path> paths = new ArrayList<Path>();

	/**
	 * 可用的许可，由子节点确定
	 */
	private Set<String> permissionCodes = new HashSet<String>();

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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		if (visible == null || "1".equals(visible))
			this.visible = true;
	}

	public List<Path> getPaths() {
		return paths;
	}

	/**
	 * 仅由Path类调用
	 * 
	 * @param p
	 */
	public void addPath(Path p) {
		p.setPathGroup(this);
		paths.add(p);
	}

	/**
	 * 仅由Path类调用
	 * 
	 * @param p
	 */
	public void addPermission(Permission p) {
		permissionCodes.add(p.getCode());
	}

	public boolean isAvailable(Admin admin) {
//		for (String c : permissionCodes)
//			if (admin.hasPermission(c))
//				return true;
//		return false;
		return true;
	}
}
