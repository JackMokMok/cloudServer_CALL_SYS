package com.cloudapp.core.constants;

import com.cloudapp.core.constants.bean.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账号状态
 */

public enum SortingRulesConstant {

	SORT_BY_AUTH_DATE("授权日期", 0), SORT_BY_OFF_DAY("离线天数", 1), SORT_BY_ID_ASC("场所序号(升)", 2),
	SORT_BY_ID_DESC("场所序号(降)", 3);
	private String name;

	private int index;

	private SortingRulesConstant(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public static Map<Integer, String> getStatusMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (SortingRulesConstant c : SortingRulesConstant.values()) {
			map.put(c.index, c.name);
		}
		return map;
	}

	public static List<Parameter> getList() {
		List<Parameter> ps = new ArrayList<Parameter>();
		for (SortingRulesConstant c : SortingRulesConstant.values()) {
			ps.add(new Parameter(c.index, c.name));
		}
		return ps;
	}

	public static String getName(int index) {
		for (SortingRulesConstant c : SortingRulesConstant.values()) {
			if (c.index == index) {
				return c.name;
			}
		}
		return null;
	}
	
	public String getName() {
		return name;
	}

	public int getIndex() {
		return index;
	}
}
