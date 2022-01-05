package com.cloudapp.core.dao.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloudapp.lib.StringUtil;

/**
 * 对分页的基本数据进行一个简单的封装
 * 
 * @author 陈远
 */
public class Page implements Serializable {

	private static final long serialVersionUID = 1L;

	public Page() {
	}

	public Page(int size) {
		this.size = size;
	}

	/**
	 * 页面，默认第一页
	 */
	private int no = 1;

	/**
	 * 每页显示的记录数，默认是20条
	 */
	private int size = 20;

	/**
	 * 总记录数
	 */
	private int totalRecord;

	/**
	 * 总页数
	 */
	private int totalPage;

	/**
	 * 除去地址中的分页
	 */
	private String urlWithoutPage;//弃用

	/**
	 * 其他的参数我们把它分装成一个Map对象
	 */
	private Map<String, Object> params = new HashMap<String, Object>();
	

	/**
	 * 查询bean
	 */
	private Object bean;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		// 在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
		int totalPage = totalRecord % size == 0 ? totalRecord / size
				: totalRecord / size + 1;
		this.setTotalPage(totalPage);//设置页面数
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public boolean isNext() {
		if (this.totalPage == 0)
			return false;
		return this.totalPage != no;
	}

	public String getUrlWithoutPage() {
		StringBuffer sb = new StringBuffer("?");
		String qs = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getQueryString();
		if (StringUtil.isValid(qs))
			qs = qs.replaceAll("no=\\d+", "");

		if (StringUtil.isValid(qs))
			sb.append(qs + "&");
		urlWithoutPage = sb.toString().replace("&&", "&");
		return urlWithoutPage;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Page [pageNo=").append(no).append(", pageSize=")
				.append(size).append(", totalPage=").append(totalPage)
				.append(", totalRecord=").append(totalRecord).append("]");
		return builder.toString();
	}

}