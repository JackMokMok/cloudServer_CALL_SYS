package com.cloudapp.core.service;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CusGroup;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CusGroupService {
	
	public List<CusGroup> findPage(Page page);

	public List<CusGroup> findAll(CusGroup group);
	
	public CusGroup get(Integer id);

	public void save(CusGroup group) throws DataAccessException;

	public void update(CusGroup group) throws DataAccessException;

	public void del(Integer id);
}