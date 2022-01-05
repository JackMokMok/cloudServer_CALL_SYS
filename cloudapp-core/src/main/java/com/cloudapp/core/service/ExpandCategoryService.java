package com.cloudapp.core.service;

import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.ExpandCategory;
import org.springframework.dao.DataAccessException;
import java.util.List;

public interface ExpandCategoryService {
	
	public List<ExpandCategory> findPage(Page page);

	public List<ExpandCategory> findAll(ExpandCategory category);
	
	public ExpandCategory get(Integer id);

	public void save(ExpandCategory category) throws DataAccessException;

	public void update(ExpandCategory category) throws DataAccessException;

	public void del(Integer id);
}