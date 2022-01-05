package com.cloudapp.web.controller.caller;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallerCategory;
import com.cloudapp.core.entity.Customer;
import com.cloudapp.core.service.CallerCategoryService;
import com.cloudapp.core.service.CallerService;
import com.cloudapp.core.service.CustomerService;
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
public class CallerCategoryController extends SysBaseController {
	
	@Resource
	private CallerCategoryService categoryService;
	
	@Resource
	private CallerService callerService;
	
	@Resource
	private CustomerService customerService;

	@RequestMapping(value = "caller-category-list.html")
	public ModelAndView callerCategoryList(Page page, CallerCategory bean) {
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
		modelMap.put("categoryList", categoryService.findPage(page));
		modelMap.put("pageInfo", page);
		modelMap.put("bean", bean);
		return assemblingModel("caller/caller-category-list", modelMap);
	}
	
	/**
	 * 编辑场所组
	 * 
	 */
	@RequestMapping(value = "edit-caller-category.html")
	public ModelAndView editCallerCategory(CallerCategory category) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		Integer id = category.getId();
		Integer customerId = category.getCustomerId();
		if (id != null) {
			category = categoryService.get(id);
			if (category == null) {
				modelMap.put(error, "非法操作");
				return assemblingModel(CommonConstants.ERROR, modelMap);
			}
			customerId = category.getCustomerId();
		}else{
			Customer customer = customerService.get(customerId);
			if(customerId == null || customer == null){
				modelMap.put(error, "非法操作");
				return assemblingModel(CommonConstants.ERROR, modelMap);
			}
		}
		
		modelMap.put("category", category);
		
		CallerCategory g = new CallerCategory();
		g.setCustomerId(customerId);
		List<CallerCategory> categoryList = categoryService.findAll(g);
		modelMap.put("categoryList", categoryList);
		return assemblingModel("caller/edit-caller-category", modelMap);
	}
	
	/**
	 * 更新/新增类别
	 */
	@RequestMapping(value = "save-caller-category.html")
	public ModelAndView saveCallerCategory(CallerCategory category) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Integer id = category.getId();
			if (id == null) {
				categoryService.save(category);
				id = category.getId();
			} else {
				categoryService.update(category);
			}
			
			Integer[] callerIds = category.getCallerIds();
			if(callerIds != null && callerIds.length > 0){
				callerService.updateCategory(category);		//批量更新场所组id
			}
		} catch (Exception e) {
			modelMap.put("message", "操作失败");
			modelMap.put("ex", "输入有误，数据库操作失败！" + e.getMessage());
			return assemblingModel(CommonConstants.ERROR, modelMap);
		}
		return new ModelAndView("redirect:caller-category-list.html?customerId=" + category.getCustomerId());
	}
	
}
