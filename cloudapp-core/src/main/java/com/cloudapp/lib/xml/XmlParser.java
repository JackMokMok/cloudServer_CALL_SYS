package com.cloudapp.lib.xml;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudapp.lib.ReflectUtil;
import com.cloudapp.lib.StringUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class XmlParser {
	private static final Logger logger = LoggerFactory
			.getLogger(XmlParser.class);

	/**
	 * 默认根节点
	 */
	private static final String COMMON_ROOT_ELEMENT = "content";

	public static Document getDoc(InputStream input) {
		SAXReader reader = new SAXReader();
		Document doc = null;

		try {
			doc = reader.read(input);
		} catch (DocumentException dex) {
            logger.error("加载XML文件失败: ' " + input + " ', " + dex.getMessage());
		} finally {
			try {
				input.close();
			} catch (Exception e) {
                logger.error("关闭XML文件失败: ' " + input + " ', " + e.getMessage());
			}
		}

		return doc;
	}

	public static String getDocStringValue(String xml) {
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
            logger.error("解析XML文件失败: ' " + xml + " ', " + e.getMessage());
		}
		return document.getStringValue();
	}

	public static List<Element> getRootElements(Document doc, String node) {
		return doc.selectNodes("//" + node);
	}

	public static List<Element> getRootElements(InputStream input, String node) {
		return getRootElements(getDoc(input), node);
	}

	public static List<Element> getChildElements(Element ele, String node) {
		return ele.selectNodes(node);
	}

	public static Object parseElement(Element ele, Class clazz) {
		return parseXml(ele, clazz);
	}

	public static String parseAttribute(Element ele, String attribute) {
		Attribute a = ele.attribute(attribute);
		return a == null ? null : a.getValue();
	}

	public static Object xmlToBean(String xml, Class clazz) {
		Object result = null;
		if (!StringUtil.isValid(xml))
			return result;

		try {
			Document document = DocumentHelper.parseText(xml);
			result = parseXml(document.getRootElement(), clazz);
		} catch (Exception e) {
            logger.error("转换XML到BEAN失败, " + e.getMessage());
		}

		return result;
	}

	/**
	 * @param bean
	 * @return
	 */
	public static String beanToXml(Object bean) {
		Document document = DocumentHelper.createDocument();
		Element ele = document.addElement(COMMON_ROOT_ELEMENT);
		parseObject(ele, bean);
		return ele.asXML();
	}

	public static void parseObject(Element element, Object object) {
		if (element == null || object == null)
			return;

		Class clazz = object.getClass();

		List<Field> fs = getClassFields(clazz);
		for (Field f : fs) {
			XmlProperty xp = f.getAnnotation(XmlProperty.class);

			if (xp == null)
				continue;

			try {
				Method m = clazz
						.getMethod(ReflectUtil.getterMethod(f.getName()));
				Object o = m.invoke(object);
				if (o == null)
					continue;

				XmlPropertyParser.parseObject(element, f, o, xp.value());
			} catch (Exception e) {
				logger.error("转解析OBJ失败（XmlParser.java）, " + e.getMessage());
			}
		}
	}

	/**
	 * 填充数据
	 * 
	 * @param element
	 * @param clazz
	 */
	public static Object parseXml(Element element, Class clazz) {
		Object o = null;
		try {
			o = clazz.newInstance();
			List<Field> fields = getClassFields(clazz);
			for (Field f : fields) {
				XmlProperty xp = f.getAnnotation(XmlProperty.class);

				if (xp == null)
					continue;

				XmlPropertyParser.parseXml(element, f, o, xp.value());
			}
		} catch (Exception e) {
            logger.error("解析XML失败[1]（XmlParser.java）, " + e.getMessage());
		}
		return o;
	}

	public static List<Object> xmlToBeans(String xml, Class clazz) {
		if(!StringUtil.isValid(xml)){
			return null;
		}
		List<Object> os = new ArrayList<Object>();
		List<Element> es = null;
		try {
			es = XmlParser.getRootElements(DocumentHelper.parseText(xml),
					XmlParser.COMMON_ROOT_ELEMENT);
		} catch (DocumentException e1) {
			logger.error("解析XML失败[2]（XmlParser.java）, " + e1.getMessage());
		}
		if (es != null) {
			for (Element e : es) {
				os.add(XmlParser.parseXml(e, clazz));
			}
		}
		return os;
	}

	// 获取自身及父类的所有属性
	private static List<Field> getClassFields(Class clazz) {
		List<Field> fs = new ArrayList<Field>();
		fs.addAll(Arrays.asList(clazz.getDeclaredFields()));
		if (clazz.getSuperclass() != null)
			fs.addAll(getClassFields(clazz.getSuperclass()));
		return fs;
	}
}
