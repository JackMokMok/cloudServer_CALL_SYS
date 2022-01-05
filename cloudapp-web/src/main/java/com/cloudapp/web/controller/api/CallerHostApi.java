package com.cloudapp.web.controller.api;

import com.cloudapp.core.entity.CallerHost;
import com.cloudapp.core.service.CallerHostHeartbeatService;
import com.cloudapp.core.service.CallerHostService;
import com.cloudapp.web.controller.common.OutputDTOBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "api/")
public class CallerHostApi {

    @Autowired
    private CallerHostService callerHostService;

    @Resource
    private CallerHostHeartbeatService heartbeatService;

    @RequestMapping(value = "online-caller-host.json")
    @ResponseBody
    public String onlineCallerHost(String callback) {
        OutputDTOBase result = new OutputDTOBase();
        result.setCallback(callback);
        try {
            CallerHost callerHost = new CallerHost();
            callerHost.setOnline(null);
            Integer allCount = callerHostService.getCount(callerHost);		//终端总数
            Integer onlineCount = heartbeatService.getCount(1);				//在线终端数
            result.setData(onlineCount + "," + (allCount-onlineCount));
            result.setMsg("成功！");
            result.setCode(200);
        } catch (Exception e) {
            result.setData(e.getMessage());
            result.setMsg("数据库操作失败！");
            result.setCode(-1);
        }
        return result.toString();
    }

}
