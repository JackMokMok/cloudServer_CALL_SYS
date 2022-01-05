package com.cloudapp.lib.xml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.cloudapp.lib.ReflectUtil;
import com.cloudapp.lib.StringUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ListXmlDataParser implements XmlDataParser {
	/**
	 * 字符串list的值属性名
	 */
	private static final String STRING_LIST_ATTRIBUTE = "value";

	@Override
	public void parseObject(Element parent, Field field, Object object) {
		// 判断是否为list
		if (field.getType().isAssignableFrom(List.class)) {
			// list的包含泛型信息的类信息
			Type t = field.getGenericType();
			if (t instanceof ParameterizedType) {
				// list中的泛型类
				Class c = (Class) ((ParameterizedType) t)
						.getActualTypeArguments()[0];
				if (c != null) {
					List l = (List) object;
					// 判断list中的内容是否为字符串，如为字符串直接设置其内容
					if (c.isAssignableFrom(String.class))
						for (Object co : l)
							parent.addElement(field.getName()).addAttribute(
									STRING_LIST_ATTRIBUTE, co.toString());
					else
						for (Object co : l)
							XmlParser.parseObject(
									parent.addElement(field.getName()), co);
				}
			}
		}
	}

	@Override
	public void parseXml(Element parent, Field field, Object object)
			throws Exception {
		List<Element> ces = parent.elements(field.getName());
		if (ces != null && ces.size() > 0)
			// 判断是否为list
			if (field.getType().isAssignableFrom(List.class)) {
				// list的包含泛型信息的类信息
				Type t = field.getGenericType();
				if (t instanceof ParameterizedType) {
					// list中的泛型类
					Class c = (Class) ((ParameterizedType) t)
							.getActualTypeArguments()[0];
					if (c != null) {
						List l = new ArrayList();
						// 判断list中的内容是否为字符串，如为字符串直接设置其内容
						if (c.isAssignableFrom(String.class)) {
							for (Element ce : ces) {
								String v = ce.attribute(STRING_LIST_ATTRIBUTE)
										.getValue();
								if (StringUtil.isValid(v))
									l.add(v);
							}
						} else {
							for (Element ce : ces)
								l.add(XmlParser.parseXml(ce, c));
						}

						Method m = object.getClass().getMethod(
								ReflectUtil.setterMethod(field.getName()),
								List.class);
						m.invoke(object, l);
					}
				}
			}
	}
}
