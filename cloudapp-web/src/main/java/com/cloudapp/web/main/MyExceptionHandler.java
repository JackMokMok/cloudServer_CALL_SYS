package com.cloudapp.web.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 是spring框架的一员，负责exception情况处理
 */

public class MyExceptionHandler implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", "操作失败！");
		model.put("ex", ex);
		// 根据不同错误转向不同页面
		return new ModelAndView("core/error", model);
	}
}
