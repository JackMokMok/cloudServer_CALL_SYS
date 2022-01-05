package com.cloudapp.web.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class SessionUtil {

	public static HttpSession getSession() {
		HttpSession session = null;
		try {
			session = getRequest().getSession();
		} catch (Exception e) {
		}
		return session;
	}

	public static HttpServletRequest getRequest() {
		return getServletRequestAttributes().getRequest();
	}
	
	public static ServletContext getServletContext(){
		return getRequest().getServletContext();
	}
	
	private static ServletRequestAttributes getServletRequestAttributes(){
		return (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
	}
	
}
