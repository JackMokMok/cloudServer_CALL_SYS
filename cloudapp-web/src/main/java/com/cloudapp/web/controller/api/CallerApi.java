package com.cloudapp.web.controller.api;

import com.cloudapp.core.entity.Caller;
import com.cloudapp.core.service.CallerService;
import com.cloudapp.web.controller.api.bean.ZtreeBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备api
 */

@Controller
@RequestMapping(value = "api/")
public class CallerApi {
	
	@Resource
	private CallerService callerService;
	
	
	/**
	 * 供jquery.ztree用的数据格式
	 * @param id
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "get-callers-by-group.json")
	@ResponseBody
	public List<ZtreeBean> getDeviceByGroup(Integer id, Integer groupId) {
		List<ZtreeBean> ztreeList = new ArrayList<ZtreeBean>();
		if(id == null && groupId==null){
			return ztreeList;
		}
		try {
			Caller c = new Caller();
			c.setCategoryId(id);
			List<Caller> cusList = callerService.findAll(c);
			
			for(Caller cus:cusList){
				Integer cid = cus.getId();
				Integer gid = cus.getGroupId();
				String name = cus.getName()+ "(" + cus.getMac() + ")";
				Boolean b = groupId == null ? false : gid.equals(groupId);
				ztreeList.add(new ZtreeBean(cid,gid,name,b,false));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ztreeList;
	}
	
	@RequestMapping(value = "get-callers-by-category.json")
	@ResponseBody
	public List<ZtreeBean> getCallersByCategory(Integer id, Integer categoryId) {
		List<ZtreeBean> ztreeList = new ArrayList<ZtreeBean>();
		if(id == null && categoryId==null){
			return ztreeList;
		}
		try {
			Caller c = new Caller();
			c.setCategoryId(id);
			List<Caller> cusList = callerService.findAll(c);
			for(Caller cus:cusList){
				Integer cid = cus.getId();
				Integer gid = cus.getCategoryId();
				String name = cus.getName()+ "(" + cus.getMac() + ")";
				Boolean b = categoryId == null ? false : gid.equals(categoryId);
				ztreeList.add(new ZtreeBean(cid,gid,name,b,false));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ztreeList;
	}
}
