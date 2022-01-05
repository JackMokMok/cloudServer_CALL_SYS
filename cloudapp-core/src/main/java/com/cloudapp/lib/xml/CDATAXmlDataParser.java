package com.cloudapp.lib.xml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.dom4j.Element;

import com.cloudapp.lib.ReflectUtil;
import com.cloudapp.lib.StringUtil;

public class CDATAXmlDataParser implements XmlDataParser {
	@Override
	public void parseObject(Element parent, Field field, Object object) {
		String s = object.toString();
		if (StringUtil.isValid(s))
			parent.addElement(field.getName()).addCDATA(s);
	}

	@Override
	public void parseXml(Element parent, Field field, Object object)
			throws Exception {
		Element e = parent.element(field.getName());
		if (e != null) {
			Method m = object.getClass().getMethod(
					ReflectUtil.setterMethod(field.getName()), String.class);
			m.invoke(object, e.getText());
		}
	}
}
