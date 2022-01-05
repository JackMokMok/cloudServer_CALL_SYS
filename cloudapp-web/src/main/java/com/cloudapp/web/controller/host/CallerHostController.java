package com.cloudapp.web.controller.host;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallerHost;
import com.cloudapp.core.entity.Customer;
import com.cloudapp.core.service.CallerHostService;
import com.cloudapp.core.service.CustomerService;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.common.OutputDTOBase;
import com.cloudapp.web.controller.common.SysBaseController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "host/")
public class CallerHostController extends SysBaseController {

    private static final Log logger = LogFactory.getLog(CallerHostController.class);

    @Autowired
    private CallerHostService callerHostService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "caller-host-list.html")
    public ModelAndView callerHostList(Page page, CallerHost bean) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if(bean.getCustomerId() == null){
            modelMap.put(error, "请选择场所后操作！");
            return assemblingModel(CommonConstants.ERROR, modelMap);
        }
        page.setBean(bean);
        Customer customer = customerService.get(bean.getCustomerId());
        if(customer == null){
            modelMap.put(error, "非法操作");
            return assemblingModel(CommonConstants.ERROR, modelMap);
        }
        modelMap.put("customer", customer);
        modelMap.put("callerHostList", callerHostService.findPage(page));
        modelMap.put("pageInfo", page);
        modelMap.put("bean", bean);
        return assemblingModel("host/caller-host-list", modelMap);
    }

    /**
     * 编辑接收器
     *
     * @param callerHost
     * @return
     */
    @RequestMapping(value = "edit-caller-host.html")
    public ModelAndView editCallerHost(CallerHost callerHost) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Integer id = callerHost.getId();
        if (id != null) {
            callerHost = callerHostService.get(id);
            if (callerHost == null) {
                modelMap.put(error, "非法操作");
                return assemblingModel(CommonConstants.ERROR, modelMap);
            }
        }
        modelMap.put("callerHost", callerHost);
        return assemblingModel("host/edit-caller-host", modelMap);
    }

    /**
     * 更新/新增接收器
     *
     * @param callerHost
     * @return
     */
    @RequestMapping(value = "save-caller-host.html")
    public ModelAndView saveCallerHost(CallerHost callerHost) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            Integer id = callerHost.getId();

            if (id == null) {
                callerHostService.save(callerHost);
            } else {
                callerHostService.update(callerHost);
            }
        } catch (Exception e) {
            modelMap.put("message", "操作失败");
            modelMap.put("ex", "输入有误，数据库操作失败！" + e.getMessage());
            return assemblingModel(CommonConstants.ERROR, modelMap);
        }
        modelMap.put("customerId", callerHost.getCustomerId());
        return new ModelAndView("redirect:caller-host-list.html",modelMap);
    }

    @RequestMapping(value = "del-caller-host.json")
    @ResponseBody
    public OutputDTOBase delCallerHost(Integer id) {
        OutputDTOBase result = new OutputDTOBase();
        try {
            callerHostService.del(id);
        } catch (Exception e) {
            result.setData(e.getMessage());
            result.setMsg("操作失败！");
            result.setCode(-1);
            logger.error("设备删除（host/del-caller-host.json）失败, " + e.getMessage());
            return result;
        }
        result.setMsg("操作成功！");
        result.setCode(200);
        return result;
    }
}
