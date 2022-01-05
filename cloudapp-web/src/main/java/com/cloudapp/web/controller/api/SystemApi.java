package com.cloudapp.web.controller.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.common.OutputDTOBase;

/**
 * 系统信息Controller
 */

@Controller
@RequestMapping(value = "api/")
public class SystemApi {
	
	/**
	 * 获取设备配置信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "now.json")
	@ResponseBody
	public String getNowTime(String callback) {
		OutputDTOBase result = new OutputDTOBase();
		result.setCallback(callback);
		try {
			result.setCode(200);
			result.setMsg("成功，" + System.currentTimeMillis());
			result.setData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("失败，触发异常！");
			result.setData("[" + e.getMessage() + "]");
		}
		return result.toString();
	}
	
	/**
	 * 获取系统版本信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "version-sys.json")
	@ResponseBody
	public String getSYSVersion(String callback) {
		OutputDTOBase result = new OutputDTOBase();
		try {
			result.setCallback(callback);
			result.setCode(200);
			result.setMsg("成功！");
			result.setData(CommonConstants.SYS_VERSION);
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("失败，触发异常！");
			result.setData("[" + e.getMessage() + "]");
		}
		return result.toString();
	}
}
