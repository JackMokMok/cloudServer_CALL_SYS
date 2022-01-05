package com.cloudapp.core.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudapp.core.constants.bean.Parameter;

/**
 * 账号状态
 */

public enum AdminStatusConstant {

	COMMON("正常", 10), DISABLE("停用", -10);
	private String name;

	private int index;

	private AdminStatusConstant(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public static Map<Integer, String> getStatusMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (AdminStatusConstant c : AdminStatusConstant.values()) {
			map.put(c.index, c.name);
		}
		return map;
	}

	public static List<Parameter> getList() {
		List<Parameter> ps = new ArrayList<Parameter>();
		for (AdminStatusConstant c : AdminStatusConstant.values()) {
			ps.add(new Parameter(c.index, c.name));
		}
		return ps;
	}

	public static String getName(int index) {
		for (AdminStatusConstant c : AdminStatusConstant.values()) {
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
