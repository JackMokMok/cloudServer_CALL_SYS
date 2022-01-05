package com.cloudapp.web.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cloudapp.core.entity.Admin;
import com.cloudapp.core.entity.FileInfo;
import com.cloudapp.core.service.FileInfoService;
import com.cloudapp.web.controller.account.constants.AdminViewName;
import com.cloudapp.web.controller.common.SysBaseController;

/**
 * 管理后台首页Controller
 */

@Controller
public class IndexController extends SysBaseController {

	@Resource
	private FileInfoService fileService;
	
	/**
	 * PC首页
	 */
	@RequestMapping(value = "index.html")
	public ModelAndView index() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Admin self = getCurrentAdmin();
		modelMap.put("self", self);
		return assemblingModel(AdminViewName.INDEX, modelMap);
	}
	
	/**
	 * 检测删除不用的文件id
	 */
	@RequestMapping(value = "check-file.html")
	public ModelAndView checkFile() {
		if(!getCurrentAdmin().getUsername().equals("develop"))
			return new ModelAndView("redirect:index.html");
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Set<Integer> usedFileSet = new HashSet<Integer>();
		
		List<FileInfo> infoList = fileService.findAll(new FileInfo());
		int count = 0;
		for(FileInfo fi: infoList){
			if(!usedFileSet.contains(fi.getId())){
				fileService.del(fi.getId());
				count++;
				System.out.print("del file_info id=" + fi.getId() + "\n");
			}
		}
		modelMap.put("successMsg", "删除文件记录：" + count + "条！");
		return assemblingModel(AdminViewName.INDEX, modelMap);
	}

}
