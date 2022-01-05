package com.cloudapp.web.controller.api;

import com.cloudapp.core.entity.AllCaller;
import com.cloudapp.core.service.AllCallerService;
import com.cloudapp.web.controller.common.OutputDTOBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
@RequestMapping(value = "api/")
public class AllCallerApi {

    @Resource
    private AllCallerService allCallerService;

    @RequestMapping(value = "set-all-caller.json")
    @ResponseBody
    private String setAllCaller(String code, String callback){
        OutputDTOBase result = new OutputDTOBase();
        result.setCallback(callback);
        if (code == null || code.equals("")){
            result.setCode(-1);
            result.setMsg("缺少参数！");
            result.setData("");
            return result.toString();
        }

        try {
            AllCaller allCaller = allCallerService.getByCode(code);
            if(allCaller != null || allCaller.getId() != null){
                result.setCode(-1);
                result.setMsg("呼叫器编码已录入！");
                result.setData("");
            }else {
                allCaller = new AllCaller();
                allCaller.setCode(code);
                allCallerService.save(allCaller);
                result.setCode(200);
                result.setMsg("成功！");
                result.setData("");
            }
        } catch (Exception e) {
            result.setCode(-1);
            result.setMsg("失败，触发异常！" + e.getMessage());
            result.setData("");
        }
        return result.toString();
    }

}
