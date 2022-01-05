package com.cloudapp.web.controller.expand;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.ExpandCategory;
import com.cloudapp.core.service.ExpandCategoryService;
import com.cloudapp.core.service.ExpandService;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.common.OutputDTOBase;
import com.cloudapp.web.controller.common.SysBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 扩展设备类型Controller
 *
 */

@Controller
@RequestMapping(value = "expands/")
public class ExpandCategoryController extends SysBaseController {
	
	@Resource
	private ExpandCategoryService categoryService;

	@RequestMapping(value = "category-list.html")
	public ModelAndView categoryList(Page page, ExpandCategory bean) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		page.setBean(bean);
		modelMap.put("categoryList", categoryService.findPage(page));
		modelMap.put("pageInfo", page);
		modelMap.put("bean", bean);
		return assemblingModel("expands/category-list", modelMap);
	}
	
	/**
	 * 编辑类型
	 * 
	 */
	@RequestMapping(value = "edit-category.html")
	public ModelAndView editExpandCategory(Integer id) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if (id != null) {
			ExpandCategory category = categoryService.get(id);
			if (category == null) {
				modelMap.put(error, "非法操作");
				return assemblingModel(CommonConstants.ERROR, modelMap);
			}
			modelMap.put("category", category);
		}
		
		return assemblingModel("expands/edit-category", modelMap);
	}
	
	/**
	 * 更新/新增类别
	 */
	@RequestMapping(value = "save-category.html")
	public ModelAndView saveExpandCategory(ExpandCategory category) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Integer id = category.getId();
			if (id == null) {
				categoryService.save(category);
			} else {
				categoryService.update(category);
			}
			
		} catch (Exception e) {
			modelMap.put("message", "操作失败");
			modelMap.put("ex", "输入有误，数据库操作失败！" + e.getMessage());
			return assemblingModel(CommonConstants.ERROR, modelMap);
		}
		return new ModelAndView("redirect:category-list.html");
	}

	@RequestMapping(value = "del-category.json")
	@ResponseBody
	public OutputDTOBase delCategory(Integer id) {
		OutputDTOBase result = new OutputDTOBase();
		try {
			categoryService.del(id);
		} catch (Exception e) {
			result.setData(e.getMessage());
			result.setMsg("操作失败！");
			result.setCode(-1);

			return result;
		}
		result.setMsg("操作成功！");
		result.setCode(200);
		return result;
	}
}
