package com.cloudapp.web.controller.caller;

import com.cloudapp.core.constants.AdminStatusConstant;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.*;
import com.cloudapp.core.service.*;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.common.OutputDTOBase;
import com.cloudapp.web.controller.common.SysBaseController;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "caller/")
public class CallerController extends SysBaseController {

    private static final Log logger = LogFactory.getLog(CallerController.class);

    @Autowired
    private CallerService callerService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CallerHostService callerHostService;

    @Autowired
    private CallerGroupService callerGroupService;

    @Autowired
    private CallerCategoryService callerCategoryService;

    /**
     * 呼叫器列表
     * @param page
     * @param bean
     * @return
     */
    @RequestMapping(value = "caller-list.html")
    public ModelAndView callerList(Page page, Caller bean) {
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
        modelMap.put("callerList", callerService.findPage(page));
        modelMap.put("pageInfo", page);
        modelMap.put("bean", bean);
        return assemblingModel("caller/caller-list", modelMap);
    }

    /**
     * 编辑呼叫器
     *
     * @param caller
     * @return
     */
    @RequestMapping(value = "edit-caller.html")
    public ModelAndView editCaller(Caller caller) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Integer id = caller.getId();
        Integer customerId = caller.getCustomerId();
        if (id != null) {
            caller = callerService.get(id);
            caller.setCustomerId(customerId);
            if (caller == null) {
                modelMap.put(error, "非法操作");
                return assemblingModel(CommonConstants.ERROR, modelMap);
            }
        }else{
            Customer customer = customerService.get(customerId);
            if(customerId == null || customer == null){
                modelMap.put(error, "非法操作");
                return assemblingModel(CommonConstants.ERROR, modelMap);
            }
        }
        CallerGroup group = new CallerGroup();
        group.setCustomerId(customerId);

        CallerCategory category = new CallerCategory();
        category.setCustomerId(customerId);

        CallerHost host = new CallerHost();
        host.setCustomerId(customerId);

        modelMap.put("groupList", callerGroupService.findAll(group));
        modelMap.put("categoryList", callerCategoryService.findAll(category));
        modelMap.put("hostList",callerHostService.findAll(host));
        modelMap.put("caller", caller);
        return assemblingModel("caller/edit-caller", modelMap);
    }


    /**
     * 更新/新增呼叫器
     *
     * @param caller
     * @return
     */
    @RequestMapping(value = "save-caller.html")
    public ModelAndView saveCaller(Caller caller) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            Integer id = caller.getId();
            if (id == null) {
                callerService.save(caller);
            } else {
                callerService.update(caller);
            }
        } catch (Exception e) {
            modelMap.put("message", "操作失败");
            modelMap.put("ex", "输入有误，数据库操作失败！" + e.getMessage());
            return assemblingModel(CommonConstants.ERROR, modelMap);
        }
        modelMap.put("customerId", caller.getCustomerId());
        return new ModelAndView("redirect:caller-list.html",modelMap);
    }

    @RequestMapping(value = "del-caller.json")
    @ResponseBody
    public OutputDTOBase delCaller(Integer[] ids) {
        OutputDTOBase result = new OutputDTOBase();
        try {
            if(ids != null && ids.length>0){
                callerService.delByArray(ids);
            }
        } catch (Exception e) {
            result.setData(e.getMessage());
            result.setMsg("操作失败！");
            result.setCode(-1);
            logger.error("设备呼叫器（caller/del-caller.json）失败, " + e.getMessage());
            return result;
        }
        result.setMsg("操作成功！");
        result.setCode(200);
        return result;
    }

    @RequestMapping(value = "empty-caller.json")
    @ResponseBody
    public OutputDTOBase emptyCaller(Integer customerId) {
        OutputDTOBase result = new OutputDTOBase();
        if(customerId ==null){
            result.setData("缺少参数！");
            result.setMsg("操作失败！");
            result.setCode(-1);
            return result;
        }
        try {
            CallerHost callerHost = new CallerHost();
            callerHost.setCustomerId(customerId);
            List<CallerHost> callerHosts = callerHostService.findAll(callerHost);
            for (CallerHost host : callerHosts) {
                callerService.delByMac(host.getMac());
            }
            result.setMsg("操作成功！");
            result.setCode(200);
            return result;
        } catch (Exception e) {
            result.setData(e.getMessage());
            result.setMsg("操作失败！");
            result.setCode(-1);
            logger.error("清空呼叫器（caller/empty-caller.json）失败, " + e.getMessage());
            return result;
        }
    }

    @RequestMapping(value = "edit-callers.html")
    public ModelAndView editCallers(Integer customerId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        Customer customer = customerService.get(customerId);
        if(customerId == null || customer == null){
            modelMap.put(error, "非法操作");
            return assemblingModel(CommonConstants.ERROR, modelMap);
        }
        modelMap.put("customerId", customerId);
        return assemblingModel("caller/edit-callers", modelMap);
    }

    @RequestMapping(value = "save-callers.json")
    @ResponseBody
    public OutputDTOBase saveCallers(Integer customerId, String callers) {
        OutputDTOBase result = new OutputDTOBase();
        int count = 0;
        String msg = "";
        try {
            CallerHost callerHost = new CallerHost();//获得呼叫主机
            callerHost.setCustomerId(customerId);
            List<CallerHost> callerHostList = callerHostService.findAll(callerHost);

            CallerGroup callerGroup = new CallerGroup();//获得该场所的呼叫器分组
            callerGroup.setCustomerId(customerId);
            List<CallerGroup> callerGroupList = callerGroupService.findAll(callerGroup);

            CallerCategory callerCategory = new CallerCategory();//获得该场所的呼叫器类型
            callerCategory.setCustomerId(customerId);
            List<CallerCategory> callerCategoryList = callerCategoryService.findAll(callerCategory);

            if(callerHostList == null || callerHostList.size() == 0){
                msg = "<font style='color:red;'>系统中未添加该场所的呼叫主机！</font><br/>";
                result.setMsg(msg);
                result.setCode(-1);
                return result;
            }

            if(callerGroupList == null || callerGroupList.size() == 0){
                msg = "<font style='color:red;'>系统中未添加该场所的呼叫器分组！</font><br/>";
                result.setMsg(msg);
                result.setCode(-1);
                return result;
            }

            if(callerCategoryList == null || callerCategoryList.size() == 0){
                msg = "系统中未添加该场所的呼叫器类型！";
                result.setMsg(msg);
                result.setCode(-1);
                return result;
            }

            String[] lines = callers.split("\n");
            for(String line:lines){
                String[] info = line.split("-");
                if(info.length == 4 || info.length == 5){
                    String mac = info[0];
                    String code = info[1];
                    String categoryName = info[2];
                    String name = info[3];
                    System.out.println(mac + "-" + code + "-" + categoryName + "-" + name);
                    String tvMac = "";
                    if(info.length == 5){
                        tvMac = info[4];
                    }
                    if (mac != null && !mac.equals("") && code != null && !code.equals("")){
                        boolean isContainMac = false;
                        for (CallerHost host : callerHostList) {
                            if(mac.equals(host.getMac())){
                                isContainMac = true;
                                break;
                            }
                        }
                        if(!isContainMac){
                            msg += "<font style='color:red;'>输入的呼叫主机MAC地址" + mac + "在系统中不存在！</font><br/>";
                            continue;
                        }

                        Caller caller = new Caller();
                        caller.setMac(mac);
                        caller.setCode(code);
                        //caller.setName(name);
                        Caller t_caller = callerService.getCaller(caller);
                        if(t_caller == null){//插入新记录
                            Caller caller1 = new Caller();
                            caller1.setMac(mac);
                            caller1.setCode(code);
                            caller1.setGroupId(callerGroupList.get(0).getId());
                            caller1.setCategoryId(callerCategoryList.get(0).getId());
                            caller1.setTvMac(tvMac);
                            caller1.setName(name);
                            callerService.save(caller1);
                        }else {//更新记录
                            Caller caller1 = new Caller();
                            caller1.setId(t_caller.getId());
                            //caller1.setMac(mac);
                            caller1.setCode(code);
                            for (CallerCategory category : callerCategoryList) {
                                if(categoryName.equals(category.getName())){
                                    caller1.setCategoryId(category.getId());
                                    break;
                                }
                            }
                            if(caller1.getCategoryId() == null){
                                msg += "<font style='color:red;'>输入的" + categoryName + "类型在系统中不存在！</font><br/>";
                                continue;
                            }
                            caller1.setTvMac(tvMac);
                            caller1.setName(name);
                            callerService.update(caller1);
                        }
                        count ++;
                    }else {
                        msg += "<font style='color:red;'>呼叫主机或呼叫器编码格式不正确！</font><br/>";
                        continue;
                    }
                }else {
                    msg += "<font style='color:red;'>该行插入记录格式不正确！</font><br/>";
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData(e.getMessage());
            result.setMsg("操作失败！");
            result.setCode(-1);
            return result;
        }
        System.out.print("Insert or update caller:count=" + count + " &customerId=" + customerId +"\n");
        msg += "成功添加或更新呼叫器数量："  + count;
        result.setMsg(msg);
        result.setCode(200);
        return result;
    }

    @RequestMapping(value = "down-callers.json")
    public void downDevices(HttpServletRequest request,HttpServletResponse response, Integer customerId) {

        File dirFile = new File(CommonConstants.RES_UPLOAD_PATH);
        if (!dirFile.exists())
            dirFile.mkdirs();

        Caller caller = new Caller();
        caller.setCustomerId(customerId);
        List<Caller> callerList = callerService.findAll(caller);
        StringBuffer line = new StringBuffer();
        for (Caller c : callerList) {
            if(c.getTvMac() != null && !c.getTvMac().equals("")){
                line.append(c.getMac() + "-" + c.getCode() + "-" + c.getCategoryName() + "-" + c.getName()  + "-" + c.getTvMac() + "\r\n");
            }else {
                line.append(c.getMac() + "-" + c.getCode() + "-" + c.getCategoryName() + "-" + c.getName()  + "\r\n");
            }

        }

        String fileName = CommonConstants.RES_UPLOAD_PATH + "/"
                + UUID.randomUUID().toString();
        BufferedWriter op;
        try {
            op = new BufferedWriter(new FileWriter(fileName));
            op.write(line.toString());
            op.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.reset();
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=\"CallerConf.txt");
        OutputStream output = null;
        FileInputStream input = null;
        File file = new File(fileName);
        try {
            output = response.getOutputStream();
            byte[] buffer = new byte[1024 * 8];
            input = new FileInputStream(file);
            while (input.available() > 0) {
                int length = input.read(buffer);
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
            output = null;
            input = null;
            file.delete();
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
