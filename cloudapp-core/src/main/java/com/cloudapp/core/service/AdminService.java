package com.cloudapp.core.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.Admin;

public interface AdminService {

	public List<Admin> findPage(Page page);

	public void updateSelfInfo(Admin admin);

	public Admin get(Integer id);

	public void save(Admin entity) throws DataAccessException;

	public void update(Admin entity) throws DataAccessException;

	public Admin login(String username, String password);
	
	public Admin loginByOpenid(String openid);
	
	public void del(String[] ids) throws DataAccessException;
	
	public void del(Integer id) throws DataAccessException;
	
	public Admin findUser(Admin user);
	
	public List<Admin> findAll(Admin admin);
	
	public Boolean isCheckName(Integer id, String name);

}