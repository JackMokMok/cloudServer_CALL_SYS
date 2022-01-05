package com.cloudapp.web.controller.api;

import com.cloudapp.core.entity.*;
import com.cloudapp.core.service.*;
import com.cloudapp.lib.GsonUtil;
import com.cloudapp.lib.HttpUtil;
import com.cloudapp.lib.TimeUtil;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.api.bean.ZtreeBean;
import com.cloudapp.web.controller.common.OutputDTOBase;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 场所api
 */

@Controller
@RequestMapping(value = "api/")
public class CustomerApi {
	
	@Resource
	private CustomerService customerService;
	
	/**
	 * 获取myWork的客户信息
	 * 由于jquery.autocomplete.js不知道怎么跨域，干脆用api转下
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "get-mywork-customer.json")
	@ResponseBody
	public String getFunc(String name) {
		String result = HttpUtil.getOpenUrl("http://" + CommonConstants.CLOUD_MY_WORK + "/api/get-customers.json?name=" + name, true, "utf-8");
		return result;
	}
	
	/**
	 * 供jquery.ztree用的数据格式
	 * @param id
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "get-customers.json")
	@ResponseBody
	public List<ZtreeBean> getCustomers(Integer id, String msg) {
		List<ZtreeBean> ztreeList = new ArrayList<ZtreeBean>();
		if(id == null){
			return ztreeList;
		}
		try {
			Customer c = new Customer();
			c.setCusGroupId(id);
			List<Customer> cusList = customerService.findAll(c);
			
			for(Customer cus:cusList){
				Integer cid = cus.getId();
				Integer gid = cus.getCusGroupId();
				String name = cus.getName();
				if(msg.equals("authDate")){
					name += "(" + TimeUtil.formatDate(cus.getAuthDate(), "yyyy-MM-dd") + ")";
				}else if(msg.equals("code")){
					name += "(" + cus.getCode() + ")";
				}
				ztreeList.add(new ZtreeBean(cid,gid,name,false,false));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ztreeList;
	}
	
	/**
	 * 供jquery.ztree用的数据格式
	 * @param id
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "get-customers-by-group.json")
	@ResponseBody
	public List<ZtreeBean> getCusByGroup(Integer id, Integer groupId) {
		List<ZtreeBean> ztreeList = new ArrayList<ZtreeBean>();
		if(id == null && groupId==null){
			return ztreeList;
		}
		try {
			Customer c = new Customer();
			c.setCusGroupId(id);
			List<Customer> cusList = customerService.findAll(c);
			
			for(Customer cus:cusList){
				Integer cid = cus.getId();
				Integer gid = cus.getCusGroupId();
				String name = cus.getName()+ "(" + cus.getCode() + ")";
				Boolean b = groupId==null?false:gid.equals(groupId);
				ztreeList.add(new ZtreeBean(cid,gid,name,b,false));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ztreeList;
	}
	
	/**
	 * 获取场所列表
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "get-my-customer.json")
	@ResponseBody
	public String getMyCustomer(String name, String callback) {
		OutputDTOBase result = new OutputDTOBase();
		result.setCallback(callback);
		name = name.replaceAll("\'", "");
		name = name == null ? null : name.trim();
		if(name != null && name != ""){
			List<Customer> customers = new ArrayList<Customer>();
			try {
				Customer c = new Customer();
				c.setName(name);
				customers = customerService.findAll(c);
				result.setData(GsonUtil.toJson(customers));
				result.setMsg("成功！");
				result.setCode(200);
			} catch (Exception e) {
				result.setData(e.getMessage());
				result.setMsg("数据库操作失败！");
				result.setCode(-1);
			}
		}else{
			result.setData("输入信息有误");
			result.setMsg("操作失败！");
			result.setCode(-1);
		}
		return result.toString();
	}
}
