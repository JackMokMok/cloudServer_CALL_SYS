package com.cloudapp.core.service;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallerCategory;
import org.springframework.dao.DataAccessException;
import java.util.List;

public interface CallerCategoryService {
	
	public List<CallerCategory> findPage(Page page);

	public List<CallerCategory> findAll(CallerCategory category);
	
	public CallerCategory get(Integer id);

	public void save(CallerCategory category) throws DataAccessException;

	public void update(CallerCategory category) throws DataAccessException;

	public void del(Integer id);
}