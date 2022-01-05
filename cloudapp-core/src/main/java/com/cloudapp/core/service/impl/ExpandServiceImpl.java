package com.cloudapp.core.service.impl;

import com.cloudapp.core.dao.ExpandDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.Expand;
import com.cloudapp.core.service.ExpandService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExpandServiceImpl implements ExpandService {

	@Resource
	private ExpandDao dao;
	
	@Override
	public List<Expand> findAll(Expand device) {
		return dao.findAll(device);
	}
	
	@Override
	public List<Expand> findAllPage(Page page) {
		return dao.findAllPage(page);
	}

	@Override
	public Expand get(Integer id) {
		return dao.get(id);
	}
	

	@Transactional(readOnly = false)
	public void save(Expand expand) throws DataAccessException {
		dao.insert(expand);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Expand expand) throws DataAccessException {
		dao.update(expand);
	}
	
	@Override
	public List<Expand> findPage(Page page) {
		return dao.findPage(page);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void del(Integer id) {
		dao.delete(id);
	}

	@Override
	public Expand getByMac(String mac) {
		return dao.getByMac(mac);
	}
}