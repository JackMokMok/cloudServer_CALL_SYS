package com.cloudapp.core.service.impl;

import com.cloudapp.core.dao.ExpandInfoDao;
import com.cloudapp.core.dao.base.Page;
import com.cloudapp.core.entity.ExpandInfo;
import com.cloudapp.core.service.ExpandInfoService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExpandInfoServiceImpl implements ExpandInfoService {

	@Resource
	private ExpandInfoDao dao;
	
	@Override
	public List<ExpandInfo> findAll(ExpandInfo info) {
		return dao.findAll(info);
	}

	@Override
	public ExpandInfo get(Integer id) {
		return dao.get(id);
	}
	
	@Override
	public ExpandInfo getByMac(String mac) {
		return dao.getByMac(mac);
	}

	@Transactional(readOnly = false)
	public void save(ExpandInfo info) throws DataAccessException {
		dao.insert(info);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(ExpandInfo info) throws DataAccessException {
		dao.update(info);
	}

	@Override
	public List<ExpandInfo> findPage(Page page) {
		return dao.findPage(page);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void del(Integer id) {
		dao.delete(id);
	}
}