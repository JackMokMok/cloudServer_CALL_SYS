package com.cloudapp.core.entity;

import com.cloudapp.core.entity.base.Entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 设备信息类，设备上传的信息
 *
 */

public class ExpandInfo implements Entity {

	private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 * mac地址
	 */
	private String mac;

	/**
	 * Build.BOARD
	 */
	private String buildBoard;

	/**
	 * Build.ID
	 */
	private String buildId;

	/**
	 * Build.MODEL
	 */
	private String buildModel;

	/**
	 * Build.DISPLAY
	 */
	private String buildDisplay;

	/**
	 * Build.TIME
	 */
	private Long buildTime;

	/**
	 * 局域网ip
	 */
	private String lanIp;

	/**
	 * 剩余存储
	 */
	private Long freeStorage;

	/**
	 * 版本名
	 */
	private String verName;

	/**
	 * 版本号
	 */
	private Integer verCode;

	/**
	 * 最近登录时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date loginTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getBuildBoard() {
		return buildBoard;
	}
	
	public void setBuildBoard(String buildBoard) {
		this.buildBoard = buildBoard;
	}
	
	public String getBuildId() {
		return buildId;
	}
	
	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}
	
	public String getBuildModel() {
		return buildModel;
	}
	
	public void setBuildModel(String buildModel) {
		this.buildModel = buildModel;
	}
	
	public Long getBuildTime() {
		return buildTime;
	}
	
	public void setBuildTime(Long buildTime) {
		this.buildTime = buildTime;
	}
	
	public String getLanIp() {
		return lanIp;
	}
	
	public void setLanIp(String lanIp) {
		this.lanIp = lanIp;
	}
	
	public Date getLoginTime() {
		return loginTime;
	}
	
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	public String getMac() {
		return mac;
	}
	
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public Long getFreeStorage() {
		return freeStorage;
	}
	
	public void setFreeStorage(Long freeStorage) {
		this.freeStorage = freeStorage;
	}
	
	public String getBuildDisplay() {
		return buildDisplay;
	}
	
	public void setBuildDisplay(String buildDisplay) {
		this.buildDisplay = buildDisplay;
	}
	
	public Integer getVerCode() {
		return verCode;
	}
	
	public void setVerCode(Integer verCode) {
		this.verCode = verCode;
	}
	
	public String getVerName() {
		return verName;
	}
	
	public void setVerName(String verName) {
		this.verName = verName;
	}
}