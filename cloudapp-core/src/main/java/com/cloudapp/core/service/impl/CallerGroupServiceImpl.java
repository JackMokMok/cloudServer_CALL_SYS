package com.cloudapp.core.service.impl;

import com.cloudapp.core.dao.CallerGroupDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CallerGroup;
import com.cloudapp.core.service.CallerGroupService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CallerGroupServiceImpl implements CallerGroupService {

	@Resource
	private CallerGroupDao dao;
	
	@Override
	public List<CallerGroup> findAll(CallerGroup group) {
		return dao.findAll(group);
	}

	@Override
	public CallerGroup get(Integer id) {
		return dao.get(id);
	}

	@Transactional(readOnly = false)
	public void save(CallerGroup group) throws DataAccessException {
		dao.insert(group);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(CallerGroup group) throws DataAccessException {
		dao.update(group);
	}

	@Override
	public List<CallerGroup> findPage(Page page) {
		return dao.findPage(page);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void del(Integer id) {
		dao.delete(id);
	}
}