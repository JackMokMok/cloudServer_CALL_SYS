package com.cloudapp.core.service.impl;

import com.cloudapp.core.dao.CusGroupDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.CusGroup;
import com.cloudapp.core.service.CusGroupService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CusGroupImpl implements CusGroupService {

	@Resource
	private CusGroupDao dao;
	
	@Override
	public List<CusGroup> findAll(CusGroup group) {
		return dao.findAll(group);
	}

	@Override
	public CusGroup get(Integer id) {
		return dao.get(id);
	}

	@Transactional(readOnly = false)
	public void save(CusGroup group) throws DataAccessException {
		dao.insert(group);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(CusGroup group) throws DataAccessException {
		dao.update(group);
	}

	@Override
	public List<CusGroup> findPage(Page page) {
		return dao.findPage(page);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void del(Integer id) {
		dao.delete(id);
	}
}