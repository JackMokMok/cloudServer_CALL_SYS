package com.cloudapp.lib;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(PropertiesUtil.class);

	/**
	 * 获取配置文件
	 * 
	 * @param propertiesPath
	 *            --配置文件绝对路径
	 * @return
	 */
	public static OrderProperties getSystemProperties(String propertiesPath) {
		//Properties ps = new Properties();
		OrderProperties ps = new OrderProperties();
		InputStream in = MetaUtil
				.getMetaDataInputStream(propertiesPath);
		try {
			ps.load(in);
		} catch (IOException e) {
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		return ps;
	}

	public static Map<String, String> getMapByProperties(String propertiesPath) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			OrderProperties p = getSystemProperties(propertiesPath);
			Iterator<Entry<Object, Object>> it = p.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Object, Object> entry = it.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				map.put((String) key, (String) value);
			}
		} catch (Exception e) {
            logger.error("配置文件加载失败, " + e.getMessage());
		}
		return map;
	}

	public static void WriteSystemProperties(String propertiesPath,
			Map<String, String> map) {
		OrderProperties ps = getSystemProperties(propertiesPath);
		OutputStream out = null;
		try {
			out = new FileOutputStream(propertiesPath);
			for (String key : map.keySet()) {
				String value = map.get(key);
				ps.setProperty(key, value);
				ps.store(out, "Update " + key);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}

	public static void WriteSystemProperties(String propertiesPath, String key,
			String value) {
		OrderProperties ps = getSystemProperties(propertiesPath);
		OutputStream out = null;
		try {
			out = new FileOutputStream(propertiesPath);
			ps.setProperty(key, value);
			ps.store(out, "Update " + key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}

	public static void RemoveProperties(String propertiesPath, String key) {
		OrderProperties ps = getSystemProperties(propertiesPath);
		OutputStream out = null;
		try {
			out = new FileOutputStream(propertiesPath);
			ps.remove(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}

}
