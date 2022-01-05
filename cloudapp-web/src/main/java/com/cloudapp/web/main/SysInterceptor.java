package com.cloudapp.web.main;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.cloudapp.core.entity.Admin;
import com.cloudapp.core.utils.PermissionUtil;
import com.cloudapp.core.utils.bean.Path;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.utils.SessionUtil;

// StartupListener.java 是spring架构初始化；SysInterceptor.java 是spring架构页面访问框架
// 是spring框架的一员，读取每一个文件之前、之后、架载完成等的回调函数，所以控制访问都在这里
// Admin.java public void setAuth(String auth) 处写死dev的权限
public class SysInterceptor implements HandlerInterceptor {

	private static final String[] suffix = { ".html", ".json" };

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		
		String servletPath = request.getServletPath();

		Admin admin = (Admin) SessionUtil.getSession().getAttribute(CommonConstants.ADMIN_SIGNON_SESSION_KEY);
		if (admin == null && !"/login.html".equals(servletPath)) {
			response.sendRedirect("/admin_login.html");
			return false;
		}

		// 获取pathCode
		String pathCode = "";
		for (String s : suffix) {
			if (servletPath.indexOf(s) != -1) {
				pathCode = servletPath.substring(1, servletPath.indexOf(s));
				break;
			}
		}
		
		// 验证pathCode
		Path path = PermissionUtil.getPath(pathCode);
		if (path == null) {
			response.sendRedirect("/forbidden.html");
			return false;
		}

		// 如果permission.xml中配置为opening和是否通过数据库中权限的验证
		if (path.isOpening() || admin.isPathAvailable(pathCode)) {

			// 无需记录日志
			if (!path.isLogEnable())
				return true;

			// 获取参数，封装成json
			Map<String, String[]> params = request.getParameterMap();
			JSONObject json = new JSONObject();
			for (String key : params.keySet()) {
				for (String value : params.get(key)) {
					if ("id".equals(key) || key.indexOf("password") != -1
							|| "submit".equals(key)) {
						continue;
					}
					json.put(key, value);
				}

			}
			
			// String logParam = json.toString();

			// 获取操作ID
			// String handleId = request.getParameter(path.getOperateKey());

			return true;
		} else {
			if(servletPath.endsWith(".json")){		//json结尾的返回纯文字
				JSONObject jsonObject = new JSONObject();
				response.setContentType("text/html");
				jsonObject.put("code", -1);
				jsonObject.put("msg", "没有权限！");
				try {
					response.getWriter().println(jsonObject.toString());
				} catch (IOException e) {
				}
			}else{
				response.sendRedirect("/forbidden.html");
			}
			return false;
		}
	}
}
