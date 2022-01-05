package com.cloudapp.web.controller.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cloudapp.core.constants.AdminStatusConstant;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.Role;
import com.cloudapp.core.entity.RolePermission;
import com.cloudapp.core.service.RolePermissionService;
import com.cloudapp.core.service.RoleService;
import com.cloudapp.core.utils.PermissionUtil;
import com.cloudapp.core.utils.bean.PathGroup;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.common.OutputDTOBase;
import com.cloudapp.web.controller.common.SysBaseController;

/**
 * 角色Controller 
 */

@Controller
@RequestMapping(value = "admin/")
public class RoleController extends SysBaseController {

	private static final Log logger = LogFactory
			.getLog(RoleController.class);
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private RolePermissionService permissionService;

	@RequestMapping(value = "role-list.html")
	public ModelAndView roleList(Page page, Role bean) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		page.setBean(bean);
		modelMap.put("roleList", roleService.findPage(page));
		modelMap.put("pageInfo", page);
		modelMap.put("bean", bean);
		return assemblingModel("admin/role-list", modelMap);
	}
	
	
	/**
	 * 编辑角色页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "edit-role.html")
	public ModelAndView editRole(Integer id) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (id != null) {
			Role role = roleService.get(id);
			if (role == null) {
				modelMap.put(error, "非法操作");
				return assemblingModel(CommonConstants.ERROR, modelMap);
			}else{
				RolePermission permission = new RolePermission();
				permission.setRoleId(id);
				List<RolePermission> roleList = permissionService.findAll(permission);
				modelMap.put("role", role);
				modelMap.put("roleList", roleList);
			}
		} 
		
		List<PathGroup> permissionGroups = new ArrayList<PathGroup>();
		List<PathGroup> groupList = PermissionUtil.getPathGroups();
		for(PathGroup pg: groupList){
			if(pg.isVisible()){
				permissionGroups.add(pg);
			}
		}
		
		
		modelMap.put("statusList", AdminStatusConstant.getList());
		modelMap.put("permissionGroups", permissionGroups);
		return assemblingModel("admin/edit-role", modelMap);
	}
	
	/**
	 * 更新/新增角色
	 * 
	 * @param pos
	 * @return
	 */
	@RequestMapping(value = "save-role.html")
	public ModelAndView saveRole(Role role) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Integer id = role.getId();
			if (id == null) {
				roleService.save(role);
				id = role.getId();
			} else {
				roleService.update(role);
				permissionService.delByRoleId(id);
			}
			String[] permissions = role.getPermissions();
			List<RolePermission> pList = new ArrayList<RolePermission>();
			if(permissions != null && permissions.length > 0){
				for(String p:permissions){
					RolePermission roleP = new RolePermission();
					roleP.setRoleId(id);
					roleP.setPermission(p);
					pList.add(roleP);
				}
				permissionService.saveList(pList);
			}
		} catch (Exception e) {
			modelMap.put("message", "操作失败");
			modelMap.put("ex", "输入有误，数据库操作失败！");
			return assemblingModel(CommonConstants.ERROR, modelMap);
		}
		return new ModelAndView("redirect:role-list.html");
	}
	
	@RequestMapping(value = "del-role.json")
	@ResponseBody
	public OutputDTOBase delRole(Integer id) {
		OutputDTOBase result = new OutputDTOBase();
		try {
			roleService.del(id);
		} catch (Exception e) {
			result.setData(e.getMessage());
			result.setMsg("操作失败！");
			result.setCode(-1);
			logger.error("角色删除（admin/del-role.json）失败, " + e.getMessage());
			return result;
		}
		result.setMsg("操作成功！");
		result.setCode(200);
		return result;
	}
}
