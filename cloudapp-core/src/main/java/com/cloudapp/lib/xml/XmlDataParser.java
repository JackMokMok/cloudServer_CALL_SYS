package com.cloudapp.lib.xml;

import java.lang.reflect.Field;

import org.dom4j.Element;

public interface XmlDataParser {
	/**
	 * @param parent
	 *            待填充的xml节点
	 * @param field
	 *            解析的字段
	 * @param object
	 *            待解析的对象
	 */
	public void parseObject(Element parent, Field field, Object object);

	/**
	 * @param parent
	 *            待解析字段隶属xml节点
	 * @param field
	 *            解析的字段
	 * @param object
	 *            用于数据填充的对象
	 * @throws Exception
	 */
	public void parseXml(Element parent, Field field, Object object)
			throws Exception;
}
