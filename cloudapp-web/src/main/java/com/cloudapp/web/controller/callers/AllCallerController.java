package com.cloudapp.web.controller.callers;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.*;
import com.cloudapp.core.service.*;
import com.cloudapp.lib.TimeUtil;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "callers/")
public class AllCallerController extends SysBaseController {

    private static final Log logger = LogFactory.getLog(AllCallerController.class);

    @Autowired
    private AllCallerService allCallerService;

    /**
     * 呼叫器列表
     * @param page
     * @param bean
     * @return
     */
    @RequestMapping(value = "all-caller-list.html")
    public ModelAndView allCallerList(Page page, AllCaller bean) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        page.setBean(bean);
        modelMap.put("allCallerList", allCallerService.findPage(page));
        modelMap.put("pageInfo", page);
        modelMap.put("bean", bean);
        return assemblingModel("callers/all-caller-list", modelMap);
    }

    /**
     * 编辑呼叫器
     *
     * @param allCaller
     * @return
     */
    @RequestMapping(value = "edit-all-caller.html")
    public ModelAndView editAllCaller(AllCaller allCaller) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Integer id = allCaller.getId();
        if (id != null) {
            allCaller = allCallerService.get(id);
            if (allCaller == null) {
                modelMap.put(error, "非法操作");
                return assemblingModel(CommonConstants.ERROR, modelMap);
            }
        }
        modelMap.put("allCaller", allCaller);
        return assemblingModel("callers/edit-all-caller", modelMap);
    }


    /**
     * 更新/新增呼叫器
     *
     * @param allCaller
     * @return
     */
    @RequestMapping(value = "save-all-caller.html")
    public ModelAndView saveAllCaller(AllCaller allCaller) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            Integer id = allCaller.getId();
            if (id == null) {
                allCallerService.save(allCaller);
            } else {
                allCallerService.update(allCaller);
            }
        } catch (Exception e) {
            modelMap.put("message", "操作失败");
            modelMap.put("ex", "输入有误，数据库操作失败！" + e.getMessage());
            return assemblingModel(CommonConstants.ERROR, modelMap);
        }
        return new ModelAndView("redirect:all-caller-list.html",modelMap);
    }

    @RequestMapping(value = "del-all-caller.json")
    @ResponseBody
    public OutputDTOBase delAllCaller(Integer[] ids) {
        OutputDTOBase result = new OutputDTOBase();
        try {
            if(ids != null && ids.length>0){
                allCallerService.delByArray(ids);
            }
        } catch (Exception e) {
            result.setData(e.getMessage());
            result.setMsg("操作失败！");
            result.setCode(-1);
            logger.error("设备呼叫器（callers/del-all-caller.json）失败, " + e.getMessage());
            return result;
        }
        result.setMsg("操作成功！");
        result.setCode(200);
        return result;
    }

}
