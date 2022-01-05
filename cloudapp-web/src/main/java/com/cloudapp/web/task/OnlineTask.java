package com.cloudapp.web.task;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cloudapp.web.constants.DomainConstants;
import com.cloudapp.web.controller.api.AdminApi;
import com.cloudapp.web.controller.api.MySqlApi;

public class OnlineTask extends QuartzJobBean {
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// MySql是否还活着
		MySqlApi.MySqlHeartbeatCheckTimeout();
		//authCode 过期检查
		AdminApi.authCodeCheckTimeout();
	}

}
