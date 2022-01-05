package com.cloudapp.lib;

import com.google.gson.Gson;

public class GsonUtil {

	public static String toJson(Object c) {
		Gson gson = new Gson();
		return gson.toJson(c);
	}

	/**
	 * json字符串转对象 eg： 单个对象 DemoBean bean = (DemoBean)GsonUtil.fromJson(jsons,
	 * DemoBean.class); 数组对象 DemoBean[] beans =
	 * (DemoBean[])GsonUtil.fromJson(jsons, DemoBean[].class);
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> Object fromJson(String json, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}

}