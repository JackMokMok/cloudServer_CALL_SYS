package com.cloudapp.lib;

import javax.servlet.http.Cookie;

public class CookieUtil {

	/**
	 * 创建Cookie
	 * 
	 * @param key
	 * @param value
	 * @param release
	 *            --是否是正式版本 true：正式版本，false：测试版本
	 * @param domain
	 *            --cookie作用域
	 * @return
	 */
	public static Cookie createCookie(String key, String value,
			boolean release, String domain) {
		Cookie c = new Cookie(key, value);
		if (release)
			c.setDomain(domain);
		c.setPath("/");
		return c;
	}
}
