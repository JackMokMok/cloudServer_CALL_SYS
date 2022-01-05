package com.cloudapp.web.controller.customer;

import com.cloudapp.core.constants.AdminStatusConstant;
import com.cloudapp.core.constants.OffDayConstant;
import com.cloudapp.core.constants.SortingRulesConstant;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallerCategory;
import com.cloudapp.core.entity.CallerGroup;
import com.cloudapp.core.entity.CusGroup;
import com.cloudapp.core.entity.Customer;
import com.cloudapp.core.service.CallerCategoryService;
import com.cloudapp.core.service.CallerGroupService;
import com.cloudapp.core.service.CusGroupService;
import com.cloudapp.core.service.CustomerService;
import com.cloudapp.lib.TimeUtil;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.common.SysBaseController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "customer/")
public class CustomerController extends SysBaseController {

    private static final Log logger = LogFactory.getLog(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Resource
    private CallerCategoryService categoryService;

    @Resource
    private CallerGroupService callerGroupService;

    @Resource
    private CusGroupService groupService;

    @RequestMapping(value = "customer-list.html")
    public ModelAndView customerList(Page page, Customer bean) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        page.setBean(bean);
        modelMap.put("customerList", customerService.findPage(page));
        modelMap.put("sortingRulesList", SortingRulesConstant.getList());
        modelMap.put("offDayList", OffDayConstant.getList());
        modelMap.put("pageInfo", page);
        modelMap.put("bean", bean);
        return assemblingModel("customer/customer-list", modelMap);
    }

    /**
     * 编辑场所
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "edit-customer.html")
    public ModelAndView editCustomer(Integer id) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (id != null) {
            Customer customer = customerService.get(id);
            if (customer == null) {
                modelMap.put(error, "非法操作");
                return assemblingModel(CommonConstants.ERROR, modelMap);
            }else{
                modelMap.put("customer", customer);
            }
        }
        modelMap.put("groupList", groupService.findAll(new CusGroup()));
        modelMap.put("confStatusList", AdminStatusConstant.getList());
        return assemblingModel("customer/edit-customer", modelMap);
    }

    /**
     * 更新/新增场所
     *
     * @param
     * @return
     */
    @RequestMapping(value = "save-customer.html")
    public ModelAndView saveCustomer(Customer customer) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            if(customerService.isCheckCus(customer)){
                modelMap.put("message", "‘" + customer.getName() + "’或者‘" + customer.getName() + "’已经存在！");
                return assemblingModel(CommonConstants.ERROR, modelMap);
            }
            Integer id = customer.getId();
            if (id == null) {
                customer.setCreateDate(TimeUtil.now());
                customerService.save(customer);
                id = customer.getId();
                //创建默认类型
                for(String c : CommonConstants.DEFAULT_CATEGORY){
                    CallerCategory category = new CallerCategory();
                    category.setCustomerId(id);
                    category.setName(c);
                    categoryService.save(category);
                }

                for(String c : CommonConstants.DEFAULT_GROUP){
                    CallerGroup group = new CallerGroup();
                    group.setCustomerId(id);
                    group.setName(c);
                    callerGroupService.save(group);
                }
            } else {
                customerService.update(customer);
            }
        } catch (Exception e) {
            modelMap.put("message", "操作失败");
            modelMap.put("ex", "输入有误，数据库操作失败！" + e.getMessage());
            return assemblingModel(CommonConstants.ERROR, modelMap);
        }
        return new ModelAndView("redirect:customer-list.html");
    }

    /**
     * 批量授权
     * @return
     */
    @RequestMapping(value = "edit-auths.html")
    public ModelAndView editAuths() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<CusGroup> groupList = groupService.findAll(new CusGroup());
        modelMap.put("groupList", groupList);
        return assemblingModel("customer/edit-auths", modelMap);
    }

    /**
     * 批量保存授权
     * @return
     */
    @RequestMapping(value = "save-auths.html")
    public ModelAndView saveAuths(Customer customer) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            Integer[] cusIds = customer.getCusIds();
            if(cusIds.length > 0 && customer.getAuthDate() != null){
                customerService.updateAuth(customer);
            }
        } catch (Exception e) {
            modelMap.put("message", "操作失败");
            modelMap.put("ex", "输入有误，数据库操作失败！" + e.getMessage());
            return assemblingModel(CommonConstants.ERROR, modelMap);
        }
        return new ModelAndView("redirect:customer-list.html");
    }

}
