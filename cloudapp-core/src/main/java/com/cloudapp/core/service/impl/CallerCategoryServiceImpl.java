package com.cloudapp.core.service.impl;

import com.cloudapp.core.dao.CallerCategoryDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallerCategory;
import com.cloudapp.core.service.CallerCategoryService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CallerCategoryServiceImpl implements CallerCategoryService {

	@Resource
	private CallerCategoryDao dao;
	
	@Override
	public List<CallerCategory> findAll(CallerCategory category) {
		return dao.findAll(category);
	}

	@Override
	public CallerCategory get(Integer id) {
		return dao.get(id);
	}

	@Transactional(readOnly = false)
	public void save(CallerCategory category) throws DataAccessException {
		dao.insert(category);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(CallerCategory category) throws DataAccessException {
		dao.update(category);
	}

	@Override
	public List<CallerCategory> findPage(Page page) {
		return dao.findPage(page);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void del(Integer id) {
		dao.delete(id);
	}
}