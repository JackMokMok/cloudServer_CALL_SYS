package com.cloudapp.lib;

import java.lang.reflect.Method;

public class ReflectUtil {
	public static String setterMethod(String field) {
		return "set" + nameToCamel(field);
	}

	public static String getterMethod(String field) {
		return "get" + nameToCamel(field);
	}

	public static String nameToCamel(String field) {
		if (!StringUtil.isValid(field))
			return "";
		return field.substring(0, 1).toUpperCase() + field.substring(1);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getFieldMethodValue(String fieldName, Class clazz, Object obj) {
		try {
			// 获得get方法
			String methodName = getterMethod(fieldName);
			Method method = clazz.getDeclaredMethod(methodName);
			return method.invoke(obj);// 执行get方法返回一个Object
		} catch (Exception e) {
			return null;
		}
	}
	
}
