package com.cloudapp.web.controller.customer;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CusGroup;
import com.cloudapp.core.service.CusGroupService;
import com.cloudapp.core.service.CustomerService;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.common.OutputDTOBase;
import com.cloudapp.web.controller.common.SysBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 场所分组
 */

@Controller
@RequestMapping(value = "customer/")
public class CusGroupController extends SysBaseController {
	
	@Resource
	private CusGroupService groupService;
	
	@Resource
	private CustomerService customerService;

	@RequestMapping(value = "group-list.html")
	public ModelAndView groupList(Page page, CusGroup bean) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		page.setBean(bean);
		modelMap.put("groupList", groupService.findPage(page));
		modelMap.put("pageInfo", page);
		modelMap.put("bean", bean);
		return assemblingModel("customer/group-list", modelMap);
	}
	
	/**
	 * 编辑场所组
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "edit-group.html")
	public ModelAndView editCusGroup(Integer id) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (id != null) {
			CusGroup group = groupService.get(id);
			if (group == null) {
				modelMap.put(error, "非法操作");
				return assemblingModel(CommonConstants.ERROR, modelMap);
			}
			modelMap.put("group", group);
		}
		List<CusGroup> groupList = groupService.findAll(new CusGroup());
		modelMap.put("groupList", groupList);
		return assemblingModel("customer/edit-group", modelMap);
	}
	
	/**
	 * 更新/新增组
	 * @param group
	 * @return
	 */
	@RequestMapping(value = "save-group.html")
	public ModelAndView saveCusGroup(CusGroup group) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Integer id = group.getId();
			if (id == null) {
				groupService.save(group);
			} else {
				groupService.update(group);
			}
			Integer[] cids = group.getCusIds();
			if(cids.length > 0){
				customerService.updateGroup(group);		//批量更新场所组id
			}
		} catch (Exception e) {
			modelMap.put("message", "操作失败");
			modelMap.put("ex", "输入有误，数据库操作失败！" + e.getMessage());
			return assemblingModel(CommonConstants.ERROR, modelMap);
		}
		return new ModelAndView("redirect:group-list.html");
	}
	
	/**
	 * 删除分组
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "del-group.json")
	@ResponseBody
	public OutputDTOBase delGroup(Integer id) {
		OutputDTOBase result = new OutputDTOBase();
		try {
			groupService.del(id);
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
