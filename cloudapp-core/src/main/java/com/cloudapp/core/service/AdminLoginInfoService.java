package com.cloudapp.core.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.AdminLoginInfo;

public interface AdminLoginInfoService {
	
	public List<AdminLoginInfo> findPage(Page page);

	public List<AdminLoginInfo> findAll(AdminLoginInfo info);
	
	public AdminLoginInfo get(Integer id);

	public void save(AdminLoginInfo info) throws DataAccessException;

	public void update(AdminLoginInfo info) throws DataAccessException;
	
	public void del(Integer id);
	
	/*
	 * 删除 day天前的信息
	 */
	public void delByDay(Integer day);
}