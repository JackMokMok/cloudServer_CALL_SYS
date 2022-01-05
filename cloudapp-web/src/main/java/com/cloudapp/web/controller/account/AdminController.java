package com.cloudapp.web.controller.account;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cloudapp.core.constants.AdminStatusConstant;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.Admin;
import com.cloudapp.core.entity.AdminLoginInfo;
import com.cloudapp.core.entity.Role;
import com.cloudapp.core.service.AdminLoginInfoService;
import com.cloudapp.core.service.AdminService;
import com.cloudapp.core.service.RoleService;
import com.cloudapp.lib.StringUtil;
import com.cloudapp.lib.TimeUtil;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.account.constants.AdminViewName;
import com.cloudapp.web.controller.common.SysBaseController;
import com.cloudapp.web.controller.common.OutputDTOBase;

/**
 * 账户Controller 
 */

@Controller
@RequestMapping(value = "admin/")
public class AdminController extends SysBaseController {

	@Resource
	private AdminService adminService;
	
	@Resource
	private AdminLoginInfoService infoService;
	
	@Resource
	private RoleService roleService;
	
	private Admin history;
	
	private boolean isBackToPage;
	
	private Page oldPage;

	@RequestMapping(value = "admin-list.html")
	public ModelAndView adminList(Page page, Admin bean) {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		if(isBackToPage && history != null){
			bean = history;
			page = oldPage;
		}
		isBackToPage = false;
		oldPage = page;
		history = bean;
		
		page.setBean(bean);
		modelMap.put("adminList", adminService.findPage(page));
		modelMap.put("roleList",roleService.findAll(new Role()));
		modelMap.put("statusList",AdminStatusConstant.getList());
		modelMap.put("pageInfo", page);
		modelMap.put("bean", bean);
		return assemblingModel(AdminViewName.ADMIN_LIST, modelMap);
	}
	
	/**
	 * 编辑账号页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "edit-admin.html")
	public ModelAndView editAdmin(Integer id) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (id != null) {
			Admin admin = adminService.get(id);
			if (admin == null || admin.getUsername().equals("develop")) {
				modelMap.put(error, "非法操作");
				return assemblingModel(CommonConstants.ERROR, modelMap);
			}
			modelMap.put("admin", admin);
		} 
		
		modelMap.put("roleList",roleService.findAll(new Role()));
		modelMap.put("statusList", AdminStatusConstant.getList());
		return assemblingModel(AdminViewName.EDIT_ADMIN, modelMap);
	}
	
	/**
	 * 更新/新增用户
	 * 
	 * @param pos
	 * @return
	 */
	@RequestMapping(value = "save-admin.html")
	public ModelAndView saveAdmin(Admin admin) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (adminService.isCheckName(admin.getId(), admin.getUsername())) {
			modelMap.put("message", "该账号已存在！");
			return assemblingModel(CommonConstants.ERROR, modelMap);
		}
		try {
			if(admin.getPassword() != null && admin.getPassword() !=""){
				admin.setPassword(StringUtil.encPassword(admin.getPassword()));
			}
			if (admin.getId() == null) {
				admin.setCreateTime(TimeUtil.now());
				adminService.save(admin);
			} else {
				adminService.update(admin);
			}
		} catch (Exception e) {
			modelMap.put("message", "操作失败");
			modelMap.put("ex", "输入有误，数据库操作失败！" + e.getMessage());
			return assemblingModel(CommonConstants.ERROR, modelMap);
		}
		isBackToPage = true;
		return new ModelAndView("redirect:admin-list.html");
	}

	
	@RequestMapping(value = "del-admin.json")
	@ResponseBody
	public OutputDTOBase delAdmin(Integer id) {
		OutputDTOBase result = new OutputDTOBase();
		result.setCode(-1);
		result.setMsg("失败！");
		if(id.equals(1)){		//develop为1，不能删除
			return result;
		}
		try {
			adminService.del(id);
			result.setCode(200);
			result.setMsg("成功！");
		} catch (Exception e) {
			
		}
		return result;
	}
	
	@RequestMapping(value = "login-history.html")
	public ModelAndView loginHistory(Page page, AdminLoginInfo bean) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		page.setBean(bean);
		modelMap.put("infoList", infoService.findPage(page));
		modelMap.put("pageInfo", page);
		modelMap.put("bean", bean);
		return assemblingModel("admin/login-info-list", modelMap);
	}
}
