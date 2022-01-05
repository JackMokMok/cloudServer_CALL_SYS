package com.cloudapp.lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {

	/**
	 * @param url
	 *            请求地址
	 * @return 返回结果
	 */
	public static String getOpenUrl(String url, boolean isUrlcode, String charset) {
		try {
			// 把参数的值转成urlcode（切记，只转参数的值）
			if(url.split("\\?").length == 2 && isUrlcode == true) {
				String domain = url.split("\\?")[0];
				String parameter = url.split("\\?")[1];
				String newParameter = "";
				for (String ele : parameter.split("&")) {
					if(ele.split("=").length == 2) {
						String k = ele.split("=")[0];
						String v = URLEncoder.encode(ele.split("=")[1], "utf-8");
						newParameter += k + "=" + v + "&";
					} else {
						newParameter += ele + "&";
					}
				}
				newParameter = newParameter.substring(0, newParameter.length()-1);
				url = domain + "?" + newParameter;
			}
			// url get请求
			StringBuffer sb = new StringBuffer();
			URL u = new URL(url);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			if (c == null) {
				return "url request error";
			} else {
				c.setConnectTimeout(2000);
				c.setReadTimeout(2000);
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(), charset));  // 指定接收的解码安符集
				int byteRead = 0;
				char[] buffer = new char[8192];
				while ((byteRead = br.read(buffer, 0, 8192)) != -1) {
					sb.append(buffer, 0, byteRead);
				}
				br.close();
				c.disconnect();
				return sb.toString();
			}
		} catch (Exception e) {
			return "url request error";
		}
	}


	public static String postOpenUrl(String url,String data) {
		if (!StringUtil.isValid(url)) {
			return "url request error";
		}
		try {
			// Send the request
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			if (conn == null) {
				return "url request error";
			}
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream());
			// write parameters
			writer.write(data);
			writer.flush();
			// Get the response
			StringBuffer answer = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			int byteRead = 0;
			char[] buffer = new char[8192];
			while ((byteRead = br.read(buffer, 0, 8192)) != -1) {
				answer.append(buffer, 0, byteRead);
			}
			writer.close();
			br.close();
			return answer.toString();
		} catch (Exception ex) {
			return "url request error";
		}
	}

	/**
	 * 获取IP，nginx环境下，nginx为反向代理
	 */
	public static String getClientIp(HttpServletRequest request) {
		String nginxIp = request.getHeader("X-Real-IP");
		if (!StringUtil.isValid(nginxIp)) {
			return request.getRemoteAddr();
		} else {
			return nginxIp;
		}
	}

}
