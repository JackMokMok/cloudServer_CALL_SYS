package com.cloudapp.web.controller.callers;

import com.cloudapp.core.constants.OnlineConstant;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallerHost;
import com.cloudapp.core.service.CallerHostService;
import com.cloudapp.web.controller.common.SysBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "callers/")
public class AllCallerHostController extends SysBaseController {

    @Autowired
    private CallerHostService callerHostService;

    @RequestMapping(value = "all-caller-host-list.html")
    public ModelAndView allCallerHost(Page page, CallerHost bean) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        page.setBean(bean);
        modelMap.put("callerHostList", callerHostService.findAllPage(page));
        modelMap.put("onlineList", OnlineConstant.getList());
        modelMap.put("pageInfo", page);
        modelMap.put("bean", bean);
        return assemblingModel("callers/all-caller-host-list", modelMap);
    }

}
