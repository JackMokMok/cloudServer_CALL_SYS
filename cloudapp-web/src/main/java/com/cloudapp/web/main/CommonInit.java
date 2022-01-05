package com.cloudapp.web.main;

import java.util.Properties;

import javax.servlet.ServletContext;

import com.cloudapp.lib.HttpUtil;
import com.cloudapp.lib.PropertiesUtil;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.constants.DatabaseConstants;
import com.cloudapp.web.constants.DomainConstants;

public abstract class CommonInit {
	
	// 发出邮件（subject与content仅限英文）
	public static String notify(String subject, String content) {
		String msg = "toaddrs=" + (CommonConstants.TO_ADDRS).replace(";", "%3b") + "&subject=CLOUDAPP_" + subject + "&content=" + content;
		return HttpUtil.getOpenUrl("http://" + CommonConstants.CLOUD_NITIFY + "/email?msg=" + msg, true, "utf-8");
	}

	/**
	 * 加载系统设置文件（往往与constants包的东西相关）
	 * 一些基础的常亮
	 */
	public static void init(ServletContext sc) {
		CommonConstants.REAL_PATH = sc.getRealPath("/");                                 // 本地路径
		
		Properties ps = PropertiesUtil                                                   // 读取系统配置文件
				.getSystemProperties(CommonConstants.REAL_PATH
						+ CommonConstants.META_PATH + "system.properties");
		DomainConstants.SYS_DOMAIN      = ps.getProperty("SYS_DOMAIN", null);            // 本系统的域名地址
		DomainConstants.UPLOAD_DOMAIN   = ps.getProperty("UPLOAD_DOMAIN", null);         // upload的域名地址
        
		CommonConstants.RES_UPLOAD_PATH = ps.getProperty("RES_UPLOAD_PATH", null);       // upload的本地地址
        CommonConstants.MYSQL_SVR = ps.getProperty("MYSQL_SVR", null);                   // MYSQL服务器信息
		CommonConstants.TYPE = ps.getProperty("TYPE","https");
		CommonConstants.CLOUD_API_SVR = ps.getProperty("CLOUD_API_SVR", null);         	 // API服务器信息

		// 数据库参数
		DatabaseConstants.database = ps.getProperty("jdbc.database", null);
		DatabaseConstants.username = ps.getProperty("jdbc.username", null);
		DatabaseConstants.password = ps.getProperty("jdbc.password", null);
	}
}
