package com.cloudapp.core.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.RolePermission;

public interface RolePermissionService {
	
	public List<RolePermission> findPage(Page page);

	public List<RolePermission> findAll(RolePermission permission);
	
	public RolePermission get(Integer id);

	public void save(RolePermission permission) throws DataAccessException;

	public void update(RolePermission permission) throws DataAccessException;
	
	public void del(Integer id);
	
	public void delByRoleId(Integer id);
	
	public void saveList(List<RolePermission> list) throws DataAccessException;
	
}