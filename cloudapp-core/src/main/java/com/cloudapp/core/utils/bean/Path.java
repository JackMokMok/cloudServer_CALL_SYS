package com.cloudapp.core.utils.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.cloudapp.core.entity.Admin;
import com.cloudapp.lib.xml.XmlProperty;
import com.cloudapp.lib.xml.XmlProperty.XmlPropertyType;

public class Path implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlProperty(XmlPropertyType.STRING)
	private String code;

	@XmlProperty(XmlPropertyType.STRING)
	private String name;

	/**
	 * 菜单显示标识
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private boolean visible = false;

	/**
	 * 日志记录标识
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private boolean logEnable = false;

	/**
	 * 日志记录的被操作对象ID标识
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private String operateKey;

	/**
	 * 开放功能标识
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private boolean opening = false;

	private PathGroup pathGroup;

	private Set<String> permissionCodes = new HashSet<String>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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
		if (visible == null || "1".equals(visible))
			this.visible = true;
	}

	public boolean isOpening() {
		return opening;
	}

	public void setOpening(String opening) {
		if (opening != null && "1".equals(opening))
			this.opening = true;
	}

	public boolean isLogEnable() {
		return logEnable;
	}

	public String getOperateKey() {
		return operateKey;
	}

	public void setOperateKey(String operateKey) {
		this.operateKey = operateKey;
	}

	public void setLogEnable(String logEnable) {
		if (logEnable != null && "1".equals(logEnable))
			this.logEnable = true;
	}

	public void addPermission(Permission p) {
		permissionCodes.add(p.getCode());
		pathGroup.addPermission(p);
	}

	public boolean isAvailable(Admin admin) {
//		for (String c : permissionCodes)
//			if (admin.hasPermission(c))
//				return true;
//		return false;
		return true;
	}

	public void setPathGroup(PathGroup pathGroup) {
		this.pathGroup = pathGroup;
	}
}
