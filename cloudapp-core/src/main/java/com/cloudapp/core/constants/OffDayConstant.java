package com.cloudapp.core.constants;

import com.cloudapp.core.constants.bean.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账号状态
 */

public enum OffDayConstant {

	OFF_NONE_HEART("暂无心跳", 0), OFF_ZERO_TO_TWO("离线0~2天", 1), OFF_THREE_TO_FIVE("离线3~5天", 2), OFF_OVER_FIVE("离线5天以上", 3);
	private String name;

	private int index;

	private OffDayConstant(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public static Map<Integer, String> getStatusMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (OffDayConstant c : OffDayConstant.values()) {
			map.put(c.index, c.name);
		}
		return map;
	}

	public static List<Parameter> getList() {
		List<Parameter> ps = new ArrayList<Parameter>();
		for (OffDayConstant c : OffDayConstant.values()) {
			ps.add(new Parameter(c.index, c.name));
		}
		return ps;
	}

	public static String getName(int index) {
		for (OffDayConstant c : OffDayConstant.values()) {
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
