package com.cloudapp.lib.xml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.dom4j.Element;

import com.cloudapp.lib.ReflectUtil;

@SuppressWarnings({ "rawtypes" })
public class ObjectXmlDataParser implements XmlDataParser {
	@Override
	public void parseObject(Element parent, Field field, Object object) {
		XmlParser.parseObject(parent.addElement(field.getName()), object);
	}

	@Override
	public void parseXml(Element parent, Field field, Object object)
			throws Exception {
		Element oe = parent.element(field.getName());
		if (oe != null) {
			Class c = (Class) field.getType();
			Method m = object.getClass().getMethod(
					ReflectUtil.setterMethod(field.getName()), c);
			m.invoke(object, XmlParser.parseXml(oe, c));
		}
	}
}
