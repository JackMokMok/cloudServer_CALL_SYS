package com.cloudapp.core.dao;


import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cloudapp.core.dao.base.Dao;
import com.cloudapp.core.dao.base.MyBatisRepository;
import com.cloudapp.core.entity.RolePermission;

@Repository
@MyBatisRepository
public interface RolePermissionDao extends Dao<RolePermission> {
	
	public void delByRoleId(Integer id) throws DataAccessException;
	
	public void saveList(List<RolePermission> list) throws DataAccessException;
}
