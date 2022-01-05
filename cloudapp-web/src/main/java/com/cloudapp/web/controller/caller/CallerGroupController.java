package com.cloudapp.web.controller.caller;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.*;
import com.cloudapp.core.service.*;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.common.SysBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 场所组Controller
 *
 */

@Controller
@RequestMapping(value = "caller/")
public class CallerGroupController extends SysBaseController {
	
	@Resource
	private CallerGroupService groupService;
	
	@Resource
	private CallerCategoryService categoryService;
	
	@Resource
	private CallerService callerService;
	
	@Resource
	private CustomerService customerService;

	@RequestMapping(value = "caller-group-list.html")
	public ModelAndView callerGroupList(Page page, CallerGroup bean) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if(bean.getCustomerId() == null){
			modelMap.put(error, "请选择场所后操作！");
			return assemblingModel(CommonConstants.ERROR, modelMap);
		}
		page.setBean(bean);
		Customer customer = customerService.get(bean.getCustomerId());
		if(customer == null){
			modelMap.put(error, "非法操作");
			return assemblingModel(CommonConstants.ERROR, modelMap);
		}
		modelMap.put("customer", customer);
		modelMap.put("groupList", groupService.findPage(page));
		modelMap.put("pageInfo", page);
		modelMap.put("bean", bean);
		return assemblingModel("caller/caller-group-list", modelMap);
	}
	
	/**
	 * 编辑场所组
	 * 
	 */
	@RequestMapping(value = "edit-caller-group.html")
	public ModelAndView editCallerGroup(CallerGroup group) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		Integer id = group.getId();
		Integer customerId = group.getCustomerId();
		if (id != null) {
			group = groupService.get(id);
			if (group == null) {
				modelMap.put(error, "非法操作");
				return assemblingModel(CommonConstants.ERROR, modelMap);
			}
			customerId = group.getCustomerId();
		}else{
			Customer customer = customerService.get(customerId);
			if(customerId == null || customer == null){
				modelMap.put(error, "非法操作");
				return assemblingModel(CommonConstants.ERROR, modelMap);
			}
		}
		
		modelMap.put("group", group);
		
		CallerCategory c = new CallerCategory();
		c.setCustomerId(customerId);
		List<CallerCategory> categoryList = categoryService.findAll(c);
		modelMap.put("categoryList", categoryList);
		return assemblingModel("caller/edit-caller-group", modelMap);
	}
	
	/**
	 * 更新/新增组
	 */
	@RequestMapping(value = "save-caller-group.html")
	public ModelAndView saveDeviceGroup(CallerGroup group) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Integer id = group.getId();
			if (id == null) {
				groupService.save(group);
				id = group.getId();
			} else {
				groupService.update(group);
			}
			
			Integer[] callerIds = group.getCallerIds();
			if(callerIds != null && callerIds.length > 0){
				callerService.updateGroup(group);		//批量更新场所组id
			}
		} catch (Exception e) {
			modelMap.put("message", "操作失败");
			modelMap.put("ex", "输入有误，数据库操作失败！" + e.getMessage());
			return assemblingModel(CommonConstants.ERROR, modelMap);
		}
		return new ModelAndView("redirect:caller-group-list.html?customerId=" + group.getCustomerId());
	}
	
}
