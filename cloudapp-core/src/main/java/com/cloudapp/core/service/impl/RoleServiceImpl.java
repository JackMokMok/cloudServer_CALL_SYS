package com.cloudapp.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudapp.core.dao.RoleDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.Role;
import com.cloudapp.core.service.RolePermissionService;
import com.cloudapp.core.service.RoleService;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleDao dao;
	
	@Resource
	private RolePermissionService permissionService;

	@Override
	public List<Role> findAll(Role role) {
		return dao.findAll(role);
	}

	@Override
	public Role get(Integer id) {
		return dao.get(id);
	}

	@Transactional(readOnly = false)
	public void save(Role role) throws DataAccessException {
		dao.insert(role);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Role role) throws DataAccessException {
		dao.update(role);
	}

	@Override
	public List<Role> findPage(Page page) {
		return dao.findPage(page);
	}
	
	@Override
	public void del(Integer id) {
		dao.delete(id);
		permissionService.delByRoleId(id);
	}
}