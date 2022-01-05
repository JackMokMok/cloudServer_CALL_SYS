package com.cloudapp.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudapp.core.dao.RolePermissionDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.RolePermission;
import com.cloudapp.core.service.RolePermissionService;

@Service
@Transactional(readOnly = true)
public class RolePermissionServiceImpl implements RolePermissionService {

	@Resource
	private RolePermissionDao dao;

	@Override
	public List<RolePermission> findAll(RolePermission permission) {
		return dao.findAll(permission);
	}

	@Override
	public RolePermission get(Integer id) {
		return dao.get(id);
	}

	@Transactional(readOnly = false)
	public void save(RolePermission permission) throws DataAccessException {
		dao.insert(permission);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(RolePermission permission) throws DataAccessException {
		dao.update(permission);
	}

	@Override
	public List<RolePermission> findPage(Page page) {
		return dao.findPage(page);
	}
	
	@Override
	public void del(Integer id) {
		dao.delete(id);
	}
	
	@Override
	public void delByRoleId(Integer id) {
		dao.delByRoleId(id);
	}
	
	@Override
	public void saveList(List<RolePermission> list) throws DataAccessException {
		dao.saveList(list);
	}

}