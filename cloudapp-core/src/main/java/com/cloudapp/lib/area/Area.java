package com.cloudapp.lib.area;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudapp.lib.MetaUtil;
import com.cloudapp.lib.area.bean.City;
import com.cloudapp.lib.area.bean.Province;
import com.cloudapp.lib.xml.XmlParser;

public class Area {
	private static final Logger logger = LoggerFactory.getLogger(Area.class);

	private static List<Province> provinces;

	private static Map<String, Province> provinceMap;

	public static Province getProvince(String code) {
		return provinceMap.get(code);
	}

	/**
	 * @param ac
	 *            地区代码，前两位为省份代码，后两位为城市代码
	 * @return
	 */
	public static City getCity(String ac) {
		return getCity(ac.substring(0, 2), ac.substring(2));
	}

	/**
	 * @param pc
	 *            省份代码
	 * @param cc
	 *            城市代码
	 * @return
	 */
	public static City getCity(String pc, String cc) {
		return getProvince(pc).getCity(cc);
	}

	/**
	 * @return
	 */
	public static List<Province> getProvinces() {
		return provinces;
	}

	public static void init(String path) {
		provinces = new ArrayList<Province>();
		provinceMap = new HashMap<String, Province>();

		try {
			List<Element> peles = XmlParser.getRootElements(
					MetaUtil.getMetaDataInputStream(path),
					"province");
			for (Element ele : peles) {
				Province p = (Province) XmlParser.parseElement(ele,
						Province.class);

				// 载入省份的所有城市
				List<Element> celes = XmlParser.getChildElements(ele, "city");
				for (Element ce : celes) {
					City c = (City) XmlParser.parseElement(ce, City.class);
					p.addCity(c);

				}

				provinces.add(p);
				provinceMap.put(p.getCode(), p);
			}
		} catch (Exception e) {
			logger.error("加载地区文件失败（省份与城市）, " + e.getMessage());
		}
	}

	public static String getAreaName(String code) {
		String ps = "省";
		String cs = "市";
		String name = null;
		if (code.length() > 2) {
			City c = getCity(code);
			if (c == null)
				name = "未知地址";
			else {
				Province p = c.getProvince();
				if (p.isSingleCity())
					name = c.getName() + cs;
				else
					name = p.getName() + ps + c.getName() + cs;
			}
		} else {
			Province p = Area.getProvince(code);
			if (p != null)
				name = Area.getProvince(code).getName() + ps;
			else {
				name = "未知地址";
			}
		}
		return name;
	}
}
