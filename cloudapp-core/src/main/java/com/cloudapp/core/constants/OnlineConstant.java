package com.cloudapp.core.constants;

import com.cloudapp.core.constants.bean.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备在线状态
 */

public enum OnlineConstant {

	ONLINE("在线", 1), OFFLINE("离线", 0);
	private String name;

	private int index;

	private OnlineConstant(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public static Map<Integer, String> getStatusMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (OnlineConstant c : OnlineConstant.values()) {
			map.put(c.index, c.name);
		}
		return map;
	}

	public static List<Parameter> getList() {
		List<Parameter> ps = new ArrayList<Parameter>();
		for (OnlineConstant c : OnlineConstant.values()) {
			ps.add(new Parameter(c.index, c.name));
		}
		return ps;
	}

	public static String getName(int index) {
		for (OnlineConstant c : OnlineConstant.values()) {
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
