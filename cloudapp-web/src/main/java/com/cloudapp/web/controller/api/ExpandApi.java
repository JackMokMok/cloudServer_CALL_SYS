package com.cloudapp.web.controller.api;

import com.cloudapp.core.entity.Expand;
import com.cloudapp.core.entity.ExpandHeartbeat;
import com.cloudapp.core.entity.ExpandInfo;
import com.cloudapp.core.service.CustomerService;
import com.cloudapp.core.service.ExpandHeartbeatService;
import com.cloudapp.core.service.ExpandInfoService;
import com.cloudapp.core.service.ExpandService;
import com.cloudapp.lib.TimeUtil;
import com.cloudapp.web.controller.common.OutputDTOBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 扩展设备api
 */

@Controller
@RequestMapping(value = "api/")
public class ExpandApi {
	
	@Resource
	private ExpandService expandService;
	
	@Resource
	private ExpandHeartbeatService heartbeatService;
	
	@Resource
	private CustomerService customerService;

	@Resource
	private ExpandInfoService expandInfoService;
	
	@Autowired
    private HttpServletRequest request;
	
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();


	/**
	 * 写入设备信息
	 *
	 * @return
	 */
	@RequestMapping(value = "set-device-info.json")
	@ResponseBody
	public String setExpandInfo(ExpandInfo info, String callback) {
		OutputDTOBase result = new OutputDTOBase();
		result.setCallback(callback);

		if(info == null || info.getMac()==null){
			result.setCode(-1);
			result.setMsg("缺少参数！");
			result.setData("");
			return result.toString();
		}

		try {
			info.setLoginTime(TimeUtil.now());
			ExpandInfo myInfo = expandInfoService.getByMac(info.getMac());
			if(myInfo == null || myInfo.getId() ==null){
				expandInfoService.save(info);
			}else{
				info.setId(myInfo.getId());
				expandInfoService.update(info);
			}
			result.setCode(200);
			result.setMsg("成功！");
			result.setData("");
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("失败，触发异常！" + e.getMessage());
			result.setData("");
		}
		return result.toString();
	}
	
	
	/**
	 * 扩展设备登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "expand-login.json")
	@ResponseBody
	public String expandLogin(String mac, Integer appVersion, String callback) {
		OutputDTOBase result = new OutputDTOBase();
		result.setCallback(callback);
		
		if(mac == null || mac.isEmpty()){
			result.setCode(-1);
			result.setMsg("缺少参数！");
			result.setData("");
			return result.toString();
		}
		try {
			Expand expand = expandService.getByMac(mac);
			
			if(expand == null || expand.getId() ==null){
				result.setCode(-2);
				result.setMsg("设备未注册！");
				result.setData("");
				return result.toString();
			}
			
			if(appVersion != null){
				expand.setAppVersion(appVersion);
				expandService.update(expand);
			}
			
			result.setCode(200);
			result.setMsg("成功！");
			result.setData(gson.toJson(expand));
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("失败，触发异常！" + e.getMessage());
			result.setData("");
		}
		return result.toString();
	}
	
	
	/**
	 * 写入扩展设备心跳
	 * 
	 * @return
	 */
	@RequestMapping(value = "expand-heartbeat.json")
	@ResponseBody
	public String expandHeartbeat(String mac, String callback) {
		OutputDTOBase result = new OutputDTOBase();
		result.setCallback(callback);
		
		if(mac == null || mac.isEmpty()){
			result.setCode(-1);
			result.setMsg("缺少参数！");
			result.setData("");
			return result.toString();
		}
		
		try {
			ExpandHeartbeat heartbeat = heartbeatService.getByMac(mac);
			if(heartbeat == null || heartbeat.getId() ==null){
				heartbeat = new ExpandHeartbeat();
				heartbeat.setMac(mac);
				heartbeat.sethTime(TimeUtil.now());
				heartbeatService.save(heartbeat);
			}else{
				heartbeat.sethTime(TimeUtil.now());
				heartbeatService.update(heartbeat);
			}
			result.setCode(200);
			result.setMsg("成功！");
			result.setData("");
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("失败，触发异常！" + e.getMessage());
			result.setData("");
		}
		return result.toString();
	}
}
