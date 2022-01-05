package com.cloudapp.web.main;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cloudapp.core.utils.PermissionUtil;
import com.cloudapp.web.constants.CommonConstants;

// StartupListener.java 是spring架构初始化；SysInterceptor.java 是spring架构页面访问框架
// 是spring框架的一员，负责初始化
public class StartupListener implements ServletContextListener {
	
	private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

	public void contextDestroyed(ServletContextEvent event) {
	}

	public void contextInitialized(ServletContextEvent event) {

		CommonInit.init(event.getServletContext());
	
		PermissionUtil.init(CommonConstants.REAL_PATH  + CommonConstants.META_PATH + "permission.xml");

		logger.warn("SYSTEM_STARTUP ...... ");
	}
}