package com.cloudapp.web.controller.expand;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.*;
import com.cloudapp.core.service.*;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.common.OutputDTOBase;
import com.cloudapp.web.controller.common.SysBaseController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 扩展设备Controller
 * @author SHUBEN
 *
 */

@Controller
@RequestMapping(value = "expand/")
public class ExpandController extends SysBaseController {
	private static final Log logger = LogFactory.getLog(ExpandController.class);
	
	@Resource
	private ExpandService expandService;

	@Resource
	private ExpandCategoryService categoryService;
	
	@Resource
	private CustomerService customerService;
	
	@Resource
	private CallerGroupService callerGroupService;

	@Resource
	private ExpandInfoService expandInfoService;
	

	@RequestMapping(value = "expand-list.html")
	public ModelAndView expandList(Page page, Expand bean) {
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
		modelMap.put("expandList", expandService.findPage(page));
		modelMap.put("categoryList", categoryService.findAll(new ExpandCategory()));
		modelMap.put("pageInfo", page);
		modelMap.put("bean", bean);
		return assemblingModel("expand/expand-list", modelMap);
	}

	@RequestMapping(value = "expand-details.html")
	public ModelAndView expandDetails(Integer id) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (id != null) {
			Expand expand = expandService.get(id);
			if (expand == null) {
				modelMap.put(error, "非法操作");
				return assemblingModel(CommonConstants.ERROR, modelMap);
			}
			ExpandInfo info = expandInfoService.getByMac(expand.getMac());
			modelMap.put("info", info);
			modelMap.put("expand", expand);
			return assemblingModel("expand/expand-details", modelMap);
		} else{
			modelMap.put(error, "非法操作");
			return assemblingModel(CommonConstants.ERROR, modelMap);
		}
	}
	
	
	/**
	 * 编辑设备
	 * 
	 * @param expand
	 * @return
	 */
	@RequestMapping(value = "edit-expand.html")
	public ModelAndView editExpand(Expand expand) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Integer id = expand.getId();
		Integer customerId = expand.getCustomerId();
		if (id != null) {
			expand = expandService.get(id);
			if (expand == null) {
				modelMap.put(error, "非法操作");
				return assemblingModel(CommonConstants.ERROR, modelMap);
			}
			customerId = expand.getCustomerId();
		} else{
			Customer customer = customerService.get(customerId);
			if(customerId == null || customer == null){
				modelMap.put(error, "非法操作");
				return assemblingModel(CommonConstants.ERROR, modelMap);
			}
		}
		modelMap.put("expand", expand);

		CallerGroup group = new CallerGroup();
		group.setCustomerId(customerId);
		modelMap.put("groupList", callerGroupService.findAll(group));
		
		ExpandCategory category = new ExpandCategory();
		modelMap.put("categoryList", categoryService.findAll(category));
		return assemblingModel("expand/edit-expand", modelMap);
	}
	
	
	/**
	 * 更新/新增扩展设备
	 * 
	 * @param expand
	 * @return
	 */
	@RequestMapping(value = "save-expand.html")
	public ModelAndView saveExpand(Expand expand) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Integer id = expand.getId();
			
			if (id == null) {
				expandService.save(expand);
			} else {
				expandService.update(expand);
			}
		} catch (Exception e) {
			modelMap.put("message", "操作失败");
			modelMap.put("ex", "输入有误，数据库操作失败！" + e.getMessage());
			return assemblingModel(CommonConstants.ERROR, modelMap);
		}
		modelMap.put("customerId", expand.getCustomerId());
		return new ModelAndView("redirect:expand-list.html",modelMap);
	}

	@RequestMapping(value = "del-expand.json")
	@ResponseBody
	public OutputDTOBase delExpand(Integer id) {
		OutputDTOBase result = new OutputDTOBase();
		try {
			expandService.del(id);
		} catch (Exception e) {
			result.setData(e.getMessage());
			result.setMsg("操作失败！");
			result.setCode(-1);
			logger.error("设备删除（expand/del-expand.json）失败, " + e.getMessage());
			return result;
		}
		result.setMsg("操作成功！");
		result.setCode(200);
		return result;
	}
}
