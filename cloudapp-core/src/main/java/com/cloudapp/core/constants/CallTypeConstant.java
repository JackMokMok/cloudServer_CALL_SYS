package com.cloudapp.core.constants;

import com.cloudapp.core.constants.bean.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 呼叫器类型
 */

public enum CallTypeConstant {

	GUEST_CALL("呼叫", "8"), CONSUME_ORDER("下单", "9"), ITEM_ARRANGE("安排", "7");
	private String name;

	private String index;

	private CallTypeConstant(String name, String index) {
		this.name = name;
		this.index = index;
	}

	public static Map<String, String> getStatusMap() {
		Map<String, String> map = new HashMap<>();
		for (CallTypeConstant c : CallTypeConstant.values()) {
			map.put(c.index, c.name);
		}
		return map;
	}

	public static List<Parameter> getList() {
		List<Parameter> ps = new ArrayList<Parameter>();
		for (CallTypeConstant c : CallTypeConstant.values()) {
			ps.add(new Parameter(c.index, c.name));
		}
		return ps;
	}

	public static String getName(String index) {
		for (CallTypeConstant c : CallTypeConstant.values()) {
			if (c.index == index) {
				return c.name;
			}
		}
		return null;
	}
	
	public String getName() {
		return name;
	}

	public String getIndex() {
		return index;
	}
}
