package com.cloudapp.web.task;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cloudapp.core.entity.FileInfo;
import com.cloudapp.core.service.AdminLoginInfoService;
import com.cloudapp.core.service.FileInfoService;
import com.cloudapp.web.constants.CommonConstants;

public class LoginInfoCheckTask extends QuartzJobBean {
	@Resource
	private AdminLoginInfoService adminLoginInfoService;
	
	@Resource
	private FileInfoService fileInfoService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			adminLoginInfoService.delByDay(30);
		} catch (Exception e) {
			System.out.print("删除过期信息失败！" + e.getMessage());
		}

		try {
			List<FileInfo> infoList = fileInfoService.findAll(new FileInfo());
			List<String> pathList = new ArrayList<String>();
			for(FileInfo i : infoList){
				pathList.add(i.getPath());
			}

			File file = new File(CommonConstants.RES_UPLOAD_PATH);		//获取其file对象
			if(file.exists()){
				File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
				for(File f:fs){					//遍历File[]数组
					if(!f.isDirectory() && pathList.size()>1 && !pathList.contains(f.getName())){		//若数据库不存在，就删了
						System.out.println("del:" + f.getName() + "\n");
						f.delete();
					}
				}
			}
		} catch (Exception e) {
			System.out.print("FileInfo！" + e.getMessage());
		}
	}
	
	public void setAdminLoginInfoService(AdminLoginInfoService adminLoginInfoService) {
		this.adminLoginInfoService = adminLoginInfoService;
	}
	
	public void setFileInfoService(FileInfoService fileInfoService) {
		this.fileInfoService = fileInfoService;
	}

}
