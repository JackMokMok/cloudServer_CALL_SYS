package com.cloudapp.lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MetaUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(MetaUtil.class);

	/**
	 * 获取文件InputStream
	 * 
	 * @param filePath 文件绝对路径
	 * @return
	 * @throws Exception
	 */
	public static InputStream getMetaDataInputStream(String filePath) {
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			logger.error("打开Bean元失败, " + e.getMessage());
		}
		return is;
	}

}
