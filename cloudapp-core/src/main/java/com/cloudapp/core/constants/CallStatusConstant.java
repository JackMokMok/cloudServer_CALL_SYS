package com.cloudapp.core.constants;

import com.cloudapp.core.constants.bean.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 呼叫状态
 */

public enum CallStatusConstant {

	CALL("正在请求", 0), FINISH("已完成", 3);
	private String name;

	private int index;

	private CallStatusConstant(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public static Map<Integer, String> getStatusMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (CallStatusConstant c : CallStatusConstant.values()) {
			map.put(c.index, c.name);
		}
		return map;
	}

	public static List<Parameter> getList() {
		List<Parameter> ps = new ArrayList<Parameter>();
		for (CallStatusConstant c : CallStatusConstant.values()) {
			ps.add(new Parameter(c.index, c.name));
		}
		return ps;
	}

	public static String getName(int index) {
		for (CallStatusConstant c : CallStatusConstant.values()) {
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
