package com.cloudapp.lib.area.bean;

import com.cloudapp.lib.xml.XmlProperty;
import com.cloudapp.lib.xml.XmlProperty.XmlPropertyType;

/**
 * 城市信息
 */
public class City {
	/**
	 * 城市编号
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private String code;

	/**
	 * 城市名称
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private String name;

	/**
	 * 所属省份
	 */
	private Province province;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

}
