package com.cloudapp.web.main;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局变量，主要是系统功能性相关的
 */

public class GlobalVariable {
	
	/**
	 * 接口登陆的用户 
	 * key：加密转hex(crop + "_" + username + "_" + System.currentTimeMillis() + "_" + expire)
	 * value：crop + "_" + username + "_" + System.currentTimeMillis() + "_" + expire "," + auth
	 */
	 public static Map<String, String> APILogin = new HashMap<String, String>();
	
	/**
	 * probe心跳
	 */
	public static Map<String, String> ProbeHeartbeat = new HashMap<String, String>();
	
	/**
	 * MySQL心跳
	 */
	public static Map<String, String> MySQLHeartbeat = new HashMap<String, String>();

	/**
	 * 最后一次获取广告的时间
	 */
	public static Map<String, String> ADLastRequest = new HashMap<String, String>();
	
	/**
	 * 生成的auth_code，用于登录
	 */
	public static Map<String, String> AuthCodes = new HashMap<String, String>();
	
}