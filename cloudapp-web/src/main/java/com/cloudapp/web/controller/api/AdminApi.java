package com.cloudapp.web.controller.api;

import java.util.Iterator;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.common.OutputDTOBase;
import com.cloudapp.web.main.GlobalVariable;
import com.cloudapp.lib.StringUtil;

/**
 * 用户信息Controller
 */

@Controller
@RequestMapping(value = "api/")
public class AdminApi {

	/**
	 * auth_code超时检查
	 * 
	 * @return
	 */
	public static void authCodeCheckTimeout() {
		long now = System.currentTimeMillis();
		Iterator<Map.Entry<String, String>> it = GlobalVariable.AuthCodes.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, String> entry = it.next();
            long setTime = Long.parseLong(entry.getValue());
            if(now >= setTime){
	           	 it.remove();    
            }
        }
	}
	
	/**
	 * 获取auth_code
	 * 
	 * @return
	 */
	@RequestMapping(value = "request_auth_code.json")
	@ResponseBody
	public String getAuthCode(String chipher, String callback) {
		OutputDTOBase result = new OutputDTOBase();
		result.setCallback(callback);
		try {
			if(chipher !=null && chipher.equals(CommonConstants.CHIPHER)){
				String authCode = StringUtil.getRandomString(10);
				GlobalVariable.AuthCodes.put(authCode, (System.currentTimeMillis()+CommonConstants.AUTH_CODE_TIMEOUT) + "");
				result.setCode(200);
				result.setMsg("成功！");
				result.setData(authCode);
			}else{
				result.setCode(-2);
				result.setMsg("失败，参数错误");
				result.setData("");
			}
		} catch (Exception e) {
			result.setCode(-1);
			result.setMsg("失败，触发异常！");
			result.setData("[" + e.getMessage() + "]");
		}
		return result.toString();
	}
}
