package com.cloudapp.core.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.Role;

public interface RoleService {
	
	public List<Role> findPage(Page page);

	public List<Role> findAll(Role role);
	
	public Role get(Integer id);

	public void save(Role role) throws DataAccessException;

	public void update(Role role) throws DataAccessException;

	public void del(Integer id);
}