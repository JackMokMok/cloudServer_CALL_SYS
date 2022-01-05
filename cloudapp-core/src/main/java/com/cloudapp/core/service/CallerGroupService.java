package com.cloudapp.core.service;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallerGroup;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CallerGroupService {
	
	public List<CallerGroup> findPage(Page page);

	public List<CallerGroup> findAll(CallerGroup group);
	
	public CallerGroup get(Integer id);

	public void save(CallerGroup group) throws DataAccessException;

	public void update(CallerGroup group) throws DataAccessException;

	public void del(Integer id);
}