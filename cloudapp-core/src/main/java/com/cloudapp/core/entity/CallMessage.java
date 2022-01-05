package com.cloudapp.core.entity;

import com.cloudapp.core.constants.CallStatusConstant;
import com.cloudapp.core.entity.base.Entity;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * 呼叫信息
 *
 */

public class CallMessage implements Entity {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	/**
	 * 场所id
	 */
	private Integer customerId;
	
	/**
	 * MAC地址
	 */
	private String code;
	
	/**
	 * 内容简体
	 */
	private String content;
	
	/**
	 * 呼叫时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date callTime;
	
	/**
	 * 完成时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date finishTime;
	
	/**
	 * 状态
	 */
	private Short status;

	public CallMessage() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	public Date getCallTime() {
		return callTime;
	}
	
	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getFinishTime() {
		return finishTime;
	}
	
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public Short getStatus() {
		return status;
	}
	
	public void setStatus(Short status) {
		this.status = status;
	}
	
	public String getStatusName() {
		String name = CallStatusConstant.getName(this.status);
		if (name == null)
			return "未知";
		return name;
	}
}