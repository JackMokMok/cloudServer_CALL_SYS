package com.cloudapp.lib.xml;

import java.lang.reflect.Field;

import org.dom4j.Element;

import com.cloudapp.lib.xml.XmlProperty.XmlPropertyType;

public class XmlPropertyParser {
	/**
	 * @param parent
	 *            待填充的xml节点
	 * @param field
	 *            解析的字段
	 * @param object
	 *            待解析的对象
	 * @param pt
	 *            解析类型
	 */
	public static void parseObject(Element parent, Field field, Object object,
			XmlPropertyType pt) {
		getParser(pt).parseObject(parent, field, object);
	}

	/**
	 * @param parent
	 *            待解析字段隶属xml节点
	 * @param field
	 *            解析的字段
	 * @param object
	 *            用于数据填充的对象
	 * @param pt
	 *            解析类型
	 * @throws Exception
	 */
	public static void parseXml(Element parent, Field field, Object object,
			XmlPropertyType pt) throws Exception {
		getParser(pt).parseXml(parent, field, object);
	}

	private static XmlDataParser getParser(XmlPropertyType pt) {
		switch (pt) {
		case INTEGER:
			return integerXmlDataParser;
		case DOUBLE:
			return doubleXmlDataParser;
		case DECIMAL:
			return decimalXmlDataParser;
		case STRING:
			return stringXmlDataParser;
		case TEXT:
			return textXmlDataParser;
		case CDATA:
			return cdataXmlDataParser;
		case OBJECT:
			return objectXmlDataParser;
		case LIST:
			return listXmlDataParser;
		}
		return null;
	}

	private static IntegerXmlDataParser integerXmlDataParser = new IntegerXmlDataParser();

	private static DoubleXmlDataParser doubleXmlDataParser = new DoubleXmlDataParser();

	private static DecimalXmlDataParser decimalXmlDataParser = new DecimalXmlDataParser();

	private static StringXmlDataParser stringXmlDataParser = new StringXmlDataParser();

	private static TextXmlDataParser textXmlDataParser = new TextXmlDataParser();

	private static CDATAXmlDataParser cdataXmlDataParser = new CDATAXmlDataParser();

	private static ObjectXmlDataParser objectXmlDataParser = new ObjectXmlDataParser();

	private static ListXmlDataParser listXmlDataParser = new ListXmlDataParser();
}
