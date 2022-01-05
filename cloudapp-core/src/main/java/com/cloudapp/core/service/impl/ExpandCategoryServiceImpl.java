package com.cloudapp.core.service.impl;

import com.cloudapp.core.dao.ExpandCategoryDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.ExpandCategory;
import com.cloudapp.core.service.ExpandCategoryService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExpandCategoryServiceImpl implements ExpandCategoryService {

	@Resource
	private ExpandCategoryDao dao;
	
	@Override
	public List<ExpandCategory> findAll(ExpandCategory category) {
		return dao.findAll(category);
	}

	@Override
	public ExpandCategory get(Integer id) {
		return dao.get(id);
	}

	@Transactional(readOnly = false)
	public void save(ExpandCategory category) throws DataAccessException {
		dao.insert(category);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(ExpandCategory category) throws DataAccessException {
		dao.update(category);
	}

	@Override
	public List<ExpandCategory> findPage(Page page) {
		return dao.findPage(page);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void del(Integer id) {
		dao.delete(id);
	}
}