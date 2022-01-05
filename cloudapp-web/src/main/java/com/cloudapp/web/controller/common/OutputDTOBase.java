package com.cloudapp.web.controller.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

/**
 * 接口调用数据返回基类
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class OutputDTOBase {
	private Integer code;

	private String msg;

	private Object data;
	
	private String callback;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}
	
	public String toString() {
		if (code != null) {                                        // code是一定不能为空的，否则就没有意义了
			String res = "{";
			res += "\"code\":" + code;                          
			if (msg != null) {
				res += "," + "\"msg\":\"" + msg.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
			}
			if (data != null) {
				res += "," + "\"data\":\"" + data.toString().replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
			}
			res += "}";
			if (callback != null) {
				res = callback + "(\"" + res.replace("\\", "\\\\").replace("\"", "\\\"") + "\")";
			}
			return res;
		} else {
			return "ERROR, CODE_IS_EMPTY!";
		}
	}

}
