package com.cloudapp.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cloudapp.lib.CheckcodeUtil;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.account.constants.AdminViewName;
import com.cloudapp.web.controller.common.SysBaseController;

/** 
 * 公共、通用性的Controller
 */

@Controller
public class CommonController extends SysBaseController {

	/**
	 * 验证码
	 * 
	 * @param os
	 * @throws IOException
	 */
	@RequestMapping(value = "checkcode.html")
	public void execute(OutputStream os) throws IOException {
		InputStream checkcodeImage = CheckcodeUtil.gen(getSession(),
				CommonConstants.CHECK_CODE_SESSION_KEY);
		FileCopyUtils.copy(checkcodeImage, os);
	}

	/**
	 * 无权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "forbidden.html")
	public ModelAndView forbidden() {
		return assemblingModel(AdminViewName.FORBIDDEN, null);
	}

	/**
	 * 404
	 * @return
	 */
	@RequestMapping(value = "404.html")
	public ModelAndView fourZeroFour() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("message", "HTTP 404- 无法找到文件");
		return assemblingModel(AdminViewName.FOURZEROFOUR, modelMap);
	}

}
