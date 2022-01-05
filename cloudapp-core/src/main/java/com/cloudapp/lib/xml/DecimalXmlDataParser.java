package com.cloudapp.lib.xml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.cloudapp.lib.ReflectUtil;
import com.cloudapp.lib.StringUtil;

public class DecimalXmlDataParser implements XmlDataParser {
	@Override
	public void parseObject(Element parent, Field field, Object object) {
		String s = object.toString();
		if (StringUtil.isValid(s))
			parent.addAttribute(field.getName(), s);
	}

	@Override
	public void parseXml(Element parent, Field field, Object object)
			throws Exception {
		Attribute a = parent.attribute(field.getName());
		if (a != null) {
			Method m = object.getClass()
					.getMethod(ReflectUtil.setterMethod(field.getName()),
							BigDecimal.class);
			m.invoke(object, new BigDecimal(a.getValue()));
		}
	}
}
