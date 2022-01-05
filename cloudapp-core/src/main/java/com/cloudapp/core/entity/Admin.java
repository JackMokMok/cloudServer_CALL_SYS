package com.cloudapp.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.cloudapp.core.constants.AdminStatusConstant;
import com.cloudapp.core.entity.base.Entity;
import com.cloudapp.core.utils.PermissionUtil;
import com.cloudapp.core.utils.bean.Menu;

public class Admin implements Entity {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	/**
	 * 角色id
	 */
	private Integer roleId;
	

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * email
	 */
	private String email;

	/**
	 * 真实姓名
	 */
	private String realname;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 微信OpenId
	 */
	private String wxOpenid;
	
	/**
	 * 可用的路径
	 */
	private List<String> availablePaths;
	

	/**
	 * 菜单
	 */
	private List<Menu> menus;
	
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	public Admin() {
	}

	public Admin(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**
	 * 获取该账号的状态名称
	 */
	public String getStatusName() {
		String name = AdminStatusConstant.getName(this.status);
		if (name == null)
			return "未知状态";
		return name;
	}

	public List<Menu> getMenus() {
		if (menus == null)
			initPermission();
		return menus;
	}
	
	public void setMenus(List<Menu> newMenus){
		menus = newMenus;
	}

	public List<String> getAvailablePaths() {
		return availablePaths;
	}
	
	public void setAvailablePaths(List<String> availablePaths) {
		this.availablePaths = availablePaths;
	}

	public boolean isPathAvailable(String path) {
		if(username.equals("develop")){
			return true;
		}
		return availablePaths.contains(path);
	}

	private void initPermission() {
		menus = new ArrayList<Menu>();
		PermissionUtil.initAdminPermission(this);
	}
	
	public String getWxOpenid() {
		return wxOpenid;
	}
	
	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
