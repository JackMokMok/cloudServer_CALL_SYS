package com.cloudapp.web.controller.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.cloudapp.core.entity.Admin;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.constants.DomainConstants;
import com.cloudapp.web.utils.SessionUtil;

/**
 * 每个页面的基类
 */

public class SysBaseController {
	

	/**
	 * ModelAndView返回错误信息值
	 */
	protected static String error = "message";

	protected Map<String, Object> commonMap;

	public SysBaseController() {
	}

	public Admin getCurrentAdmin() {
		return (Admin) getSession().getAttribute(
				CommonConstants.ADMIN_SIGNON_SESSION_KEY);
	}

	public void removeCurrentAdmin() {
		getSession().removeAttribute(CommonConstants.ADMIN_SIGNON_SESSION_KEY);
	}

	protected ModelAndView assemblingModel(String viewName,
			Map<String, Object> map) {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		Admin admin = (Admin) getSession().getAttribute(
				CommonConstants.ADMIN_SIGNON_SESSION_KEY);
		if (admin != null) {
			map.put("self", admin);
			map.put("cropnum", "");
		}
		
		String serverIp = SessionUtil.getRequest().getServerName();
		
		Integer serverPort = SessionUtil.getRequest().getServerPort();

		String type = "https";
		if(CommonConstants.TYPE.equals("http")){
			type = "http";
		}
		String sysBase = type + "://" + serverIp + ":" + serverPort;
		commonMap = new HashMap<String, Object>();
		commonMap.put("sysBase", sysBase);
		commonMap.put("resBase", sysBase + CommonConstants.WEBRES_PATH);
		commonMap.put("imgBase", sysBase + DomainConstants.UPLOAD_DOMAIN);
		commonMap.put("SYS_VERSION", CommonConstants.SYS_VERSION);
		
		map.putAll(commonMap);
		
		return new ModelAndView(viewName, map);
	}

	protected HttpSession getSession() {
		return SessionUtil.getSession();
	}
}
