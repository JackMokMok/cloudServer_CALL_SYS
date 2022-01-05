package com.cloudapp.web.controller.message;

import com.cloudapp.core.constants.CallStatusConstant;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallMessage;
import com.cloudapp.core.entity.Customer;
import com.cloudapp.core.service.CallMessageService;
import com.cloudapp.core.service.CustomerService;
import com.cloudapp.lib.TimeUtil;
import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.controller.common.OutputDTOBase;
import com.cloudapp.web.controller.common.SysBaseController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 呼叫信息Controller
 *
 */

@Controller
@RequestMapping(value = "message/")
public class CallMessageController extends SysBaseController {
	private static final Log logger = LogFactory
			.getLog(CallMessageController.class);
	
	@Resource
	private CallMessageService callService;
	
	@Resource
	private CustomerService customerService;
	
	@RequestMapping(value = "call-list.html")
	public ModelAndView callList(Page page, CallMessage bean) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if(bean.getCustomerId() == null){
			modelMap.put(error, "请选择场所后操作！");
			return assemblingModel(CommonConstants.ERROR, modelMap);
		}
		
		Customer customer = customerService.get(bean.getCustomerId());
		if(customer == null){
			modelMap.put(error, "非法操作");
			return assemblingModel(CommonConstants.ERROR, modelMap);
		}
		modelMap.put("customer", customer);
		
		page.setBean(bean);
		modelMap.put("callList", callService.findPage(page));
		modelMap.put("statusList", CallStatusConstant.getList());
		modelMap.put("pageInfo", page);
		modelMap.put("bean", bean);
		return assemblingModel("message/call-list", modelMap);
	}
	
	
	@RequestMapping(value = "finish-call.json")
	@ResponseBody
	public OutputDTOBase finishCall(Integer id, Integer customerId) {
		OutputDTOBase result = new OutputDTOBase();
		try {
			if(id != null){
				CallMessage msg = callService.get(id);
				if(msg != null){
					msg.setStatus((short) CallStatusConstant.FINISH.getIndex());
					msg.setFinishTime(TimeUtil.now());
					callService.update(msg);
				}
			} else if(customerId != null){
				CallMessage msg = new CallMessage();
				msg.setCustomerId(customerId);
				msg.setStatus((short) CallStatusConstant.FINISH.getIndex());
				msg.setFinishTime(TimeUtil.now());
				callService.updateByCustomer(msg);
			}
		} catch (Exception e) {
			result.setData(e.getMessage());
			result.setMsg("操作失败！");
			result.setCode(-1);
			logger.error("呼叫更新（message/finish-call.json）失败, " + e.getMessage());
			return result;
		}
		result.setMsg("操作成功！");
		result.setCode(200);
		return result;
	}
	
	/**
	 * 清空呼叫
	 * customerId
	 */
	@RequestMapping(value = "empty-call.json")
	@ResponseBody
	public OutputDTOBase emptyCallMessage(Integer customerId) {
		OutputDTOBase result = new OutputDTOBase();
		if(customerId ==null){
			result.setData("缺少参数！");
			result.setMsg("操作失败！");
			result.setCode(-1);
			return result;
		}
		try {
			callService.delByCustomerId(customerId);
			result.setMsg("操作成功！");
			result.setCode(200);
			return result;
		} catch (Exception e) {
			result.setData(e.getMessage());
			result.setMsg("操作失败！");
			result.setCode(-1);
			logger.error("设备删除（message/empty-call.json）失败, " + e.getMessage());
			return result;
		}
	}
}
