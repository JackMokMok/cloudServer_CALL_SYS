package com.cloudapp.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cloudapp.core.entity.Admin;
import com.cloudapp.core.entity.AdminLoginInfo;
import com.cloudapp.core.service.AdminLoginInfoService;
import com.cloudapp.core.service.AdminService;
import com.cloudapp.core.utils.bean.Menu;
import com.cloudapp.lib.CheckMobile;
import com.cloudapp.lib.CheckcodeUtil;
import com.cloudapp.lib.StringUtil;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.account.constants.AdminViewName;
import com.cloudapp.web.controller.common.SysBaseController;
import com.cloudapp.web.main.GlobalVariable;

/**
 * 其中，admin_login.html, login.html, logout.html 在springmvc-servlet.xml限制胡
 */

@Controller
public class AuthController extends SysBaseController {

	private static final Log logger = LogFactory.getLog(AuthController.class);

	@Resource
	private AdminService adminService;
	
	@Resource
	private AdminLoginInfoService infoService;

	@Autowired
    private HttpServletRequest request;
	
	/**
	 * 登录页面(未进入)
	 */
	@RequestMapping(value = "admin_login.html")
	public ModelAndView login(String username) {
		if (getCurrentAdmin() != null) {
			return new ModelAndView("redirect:index.html");
		} else {
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("user", username);
			modelMap.put("version", CommonConstants.SYS_VERSION);
			modelMap.put("device", CheckMobile.check(request.getHeader("user-agent")));    // 如果为true表示是手机，false表示为电脑
			return assemblingModel("core/login", modelMap);
		}
	}

	/**
	 * 登录（验证，并进入）
	 */
	@RequestMapping(value = "login.html")
	public ModelAndView login(String username, String password, String checkcode, String wx_openid, String auth_code) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Admin admin = null;

		if(StringUtil.isValid(wx_openid) && StringUtil.isValid(auth_code) && GlobalVariable.AuthCodes.containsKey(auth_code)){
			try {
				admin = adminService.loginByOpenid(wx_openid);
			} catch (Exception e) {
	            logger.error("用户登出失败, " + e.getMessage());
			}
		}else{
			if (!StringUtil.isValid(username) || !StringUtil.isValid(password)) {
				modelMap.put(error, "用户名或密码错误");
				modelMap.put("user", username);
				modelMap.put("version", CommonConstants.SYS_VERSION);
				modelMap.put("device", CheckMobile.check(request.getHeader("user-agent")));
				return assemblingModel(AdminViewName.LOGIN, modelMap);
			}
			
			// 如果checkcode为空，就不检查checkcode，是为了用二维码直接扫描登录（本来要验证checkcode不能为null的）
			if (checkcode != null && !CheckcodeUtil.check(getSession(), checkcode,
					CommonConstants.CHECK_CODE_SESSION_KEY)) {
				modelMap.put(error, "验证码错误");
				modelMap.put("user", username);
				modelMap.put("version", CommonConstants.SYS_VERSION);
				modelMap.put("device", CheckMobile.check(request.getHeader("user-agent")));
				return assemblingModel(AdminViewName.LOGIN, modelMap);
			}
			
			try {
				admin = adminService.login(username, password);
			} catch (Exception e) {
	            logger.error("用户登出失败, " + e.getMessage());
			}
		}

		if (admin == null) {
			modelMap.put(error, "用户名或密码错误");
			modelMap.put("user", username);
            modelMap.put("version", CommonConstants.SYS_VERSION);
            modelMap.put("device", CheckMobile.check(request.getHeader("user-agent")));
			return assemblingModel(AdminViewName.LOGIN, modelMap);
		} else {
			try{
				AdminLoginInfo info = new AdminLoginInfo();
				info.setAdminId(admin.getId());
				String lIp = request.getHeader("x-forwarded-for");
				info.setLoginIp(lIp==null?request.getRemoteAddr():lIp);
				infoService.save(info);
			}catch(Exception e){
				logger.error("登录信息插入失败！ " + e.getMessage());
			}
			
			List<Menu> tmpMenus = admin.getMenus();
			admin.setMenus(tmpMenus);
			getSession().setAttribute(CommonConstants.ADMIN_SIGNON_SESSION_KEY, admin);
			if(admin.getPassword().equals(StringUtil.encPassword("123456"))){
				return new ModelAndView("redirect:edit-pwd.html");
			}
			return new ModelAndView("redirect:index.html");
		}
	}

	/**
	 * 登出
	 */
	@RequestMapping(value = "logout.html")
	public ModelAndView logout() {
		removeCurrentAdmin();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("version", CommonConstants.SYS_VERSION);
		modelMap.put("device", CheckMobile.check(request.getHeader("user-agent")));
		return assemblingModel(AdminViewName.LOGIN, modelMap);
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value = "edit-pwd.html")
	public ModelAndView editMyPwd() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		return assemblingModel("admin/edit-pwd", modelMap);
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "pwd.html")
	public ModelAndView editPwd() {
		return assemblingModel(AdminViewName.ADMIN_PWD, null);
	}
	
	/**
	 * 绑定openid
	 */
	@RequestMapping(value = "bind-openid.html")
	public ModelAndView editPwd(String openid) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if(openid == null || openid == ""){
			modelMap.put("message", "openid不能为空！");
		}else{
			Admin admin = getCurrentAdmin();
			admin.setWxOpenid(openid);
			try {
				adminService.update(admin);
				modelMap.put("message", "成功");
			} catch (Exception e) {
				modelMap.put("message", "失败！" + e.getMessage());
			}
			
		}
		return assemblingModel(CommonConstants.MYMESSAGE, modelMap);
	}
	
	@RequestMapping(value = "update-default-pwd.html")
	public ModelAndView updateDefaultPwd(String password) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!StringUtil.isValid(password)) {
			modelMap.put(error, "请输入密码");
			return assemblingModel("admin/edit-pwd", modelMap);
		}
		Admin admin = getCurrentAdmin();
		admin.setPassword(StringUtil.encPassword(password));
		adminService.updateSelfInfo(admin);
		
		modelMap.put("username", admin.getUsername());
		modelMap.put("password", password);
		removeCurrentAdmin();
		return new ModelAndView("redirect:login.html",modelMap);
	}

	@RequestMapping(value = "update-pwd.html")
	public ModelAndView updatePwd(String opassword, String password) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!StringUtil.isValid(password) || !StringUtil.isValid(opassword)) {
			modelMap.put(error, "请输入密码");
			return assemblingModel(AdminViewName.ADMIN_PWD, modelMap);
		}
		Admin admin = getCurrentAdmin();
		if (!StringUtil.encPassword(opassword).equals(admin.getPassword())) {
			modelMap.put(error, "原始密码错误！");
			return assemblingModel(AdminViewName.ADMIN_PWD, modelMap);
		}
		admin.setPassword(StringUtil.encPassword(password));
		adminService.updateSelfInfo(admin);
		/*modelMap.put("username", "dev");
		modelMap.put("password", "93bygrjdb");
		return new ModelAndView(new RedirectView("http://cloud.szpretv.com:8085/login.html"),modelMap);*/
		return new ModelAndView("redirect:logout.html");
	}
}
