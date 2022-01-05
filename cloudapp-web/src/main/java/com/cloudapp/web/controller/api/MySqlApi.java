package com.cloudapp.web.controller.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudapp.web.constants.CommonConstants;
import com.cloudapp.web.constants.DatabaseConstants;
import com.cloudapp.web.constants.DomainConstants;
import com.cloudapp.web.main.CommonInit;
import com.cloudapp.web.main.GlobalVariable;

/**
 * 本系统的MySql接口Controller（非数据库读写）
 */

@Controller
@RequestMapping(value = "api/")
public class MySqlApi {
	
	// 检查MySql是否还活着,活着则返回true，否则返回false
	private static boolean checkMySqlAlive() {
		try {
			// 备注：本系统所在的服务器要安装有mysqladmin工具
			String cmd = "mysqladmin ping -P " + CommonConstants.MYSQL_SVR.split(":")[1] + " -u" + DatabaseConstants.username + " -p" + 
			DatabaseConstants.password + " -h " + CommonConstants.MYSQL_SVR.split(":")[0]; 
			Runtime run = Runtime.getRuntime();
			Process p = run.exec(cmd);
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());  
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
			String tmp = "";
			String content = "";
			while ((tmp = inBr.readLine()) != null) {
				content += tmp;
			}
            if (content.contains("mysqld is alive")) {
            	return true;
            }
		} catch (IOException e) {
		}
		return false;
	}
	
	// 检查MySql是否还活着
	public static void MySqlHeartbeatCheckTimeout() {
		if(checkMySqlAlive() == true) {
			GlobalVariable.MySQLHeartbeat.put("MYSQL", "" + System.currentTimeMillis());
		} else {
			if (GlobalVariable.MySQLHeartbeat.containsKey("MYSQL")) {
				CommonInit.notify("MySQL_TIMEOUT", "MySQL_NOT_ALIVE");//发送邮件
				GlobalVariable.MySQLHeartbeat.remove("MYSQL");
			}
		}
	}
	
	/**
	 * 查看mysql心跳情况
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "mysql-heartbeat-show.json")
	public void mysqlHeartbeatShow(HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";
		try {
			result += "<html>";
			result += "<b>* MYSQL最后心跳时间(MySQLHeartbeat)：</b><br/><br/>";
			result += "<table border='2' bordercolor='#000000' style='border-collapse:collapse;margin-top:10px;'>";
			for (String k : GlobalVariable.MySQLHeartbeat.keySet()) {
				result += "<tr><td><b>&nbsp;" + k + "&nbsp;</b></td><td>&nbsp;" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.parseLong(GlobalVariable.MySQLHeartbeat.get(k))) + "&nbsp;</td></tr>";
			}
			result += "</table>";
			result += "</html>";
		} catch (Exception e) {
			result = "[" + e.getMessage() + "]";
		}
		response.setContentType("text/html; charset=UTF-8");
		try {
			response.getOutputStream().write(result.getBytes("utf8"));
		} catch (IOException e) {
		}
	}
	
}
