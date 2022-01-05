package com.cloudapp.lib.area.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudapp.lib.xml.XmlProperty;
import com.cloudapp.lib.xml.XmlProperty.XmlPropertyType;

/**
 * 省份信息
 */

public class Province {
	/**
	 * 省份编号
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private String code;

	/**
	 * 省份名
	 */
	@XmlProperty(XmlPropertyType.STRING)
	private String name;

	/**
	 * 城市列表
	 */
	private List<City> cities = new ArrayList<City>();

	/**
	 * 所辖城市map
	 */
	private Map<String, City> cityMap = new HashMap<String, City>();

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

	/**
	 * @param code
	 *            城市代码
	 * @param name
	 *            城市名称
	 */
	public void addCity(City city) {
		city.setProvince(this);
		cities.add(city);
		cityMap.put(city.getCode(), city);
	}

	/**
	 * 通过城市code获得城市信息
	 * 
	 * @param code
	 * @return
	 */
	public City getCity(String code) {
		return cityMap.get(code);
	}

	/**
	 * @return 是否只有唯一城市
	 */
	public boolean isSingleCity() {
		if (cities.size() == 1)
			return true;
		return false;
	}
	
	public List<City> getCities(){
		return cities;
	}
}
